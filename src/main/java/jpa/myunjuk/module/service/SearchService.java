package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.DuplicateException;
import jpa.myunjuk.infra.exception.InvalidReqBodyException;
import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.BookStatus;
import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.search.*;
import jpa.myunjuk.module.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    @Value("${naver.id}")
    private String id;

    @Value("${naver.secret}")
    private String secret;

    @Value("${aladin.url}")
    private String pageUrl;
    private final String SEARCH_URL = "https://openapi.naver.com/v1/search/book.json?display=20";
    private final String DETAIL_URL = "https://openapi.naver.com/v1/search/book_adv.json";

    private final BookRepository bookRepository;
    private final CharactersService charactersService;

    /**
     * search
     *
     * @param keyword
     * @param start
     * @return SearchDto
     */
    public SearchDto search(String keyword, int start) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = getHttpEntity();
        URI targetUrl = UriComponentsBuilder
                .fromUriString(SEARCH_URL)
                .queryParam("query", keyword)
                .queryParam("start", start)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        return restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, SearchDto.class).getBody();
    }

    /**
     * searchDetail
     *
     * @param isbn
     * @return SearchResDto
     */
    public SearchResDto searchDetail(String isbn) {
        List<SearchDetailDto.Items> items = searchInfo(isbn).getItems();
        if (searchInfo(isbn).getItems().isEmpty() || !Pattern.matches("^.{10}\\s.{13}$", isbn)) //검색 결과가 없거나, isbn 형식이 잘못됐을 때
            throw new InvalidReqParamException("isbn = " + isbn);

        SearchDetailDto.Items item = items.get(0);
        String[] str = isbn.split(" ");
        Integer page = pageInfo(str[1]); //13자리 isbn
        return SearchResDto.builder()
                .title(item.title)
                .url(item.link)
                .thumbnail(item.image)
                .author(item.author)
                .publisher(item.publisher)
                .isbn(item.isbn)
                .description(item.description)
                .totPage(page)
                .build();
    }

    private SearchDetailDto searchInfo(String isbn) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = getHttpEntity();
        URI targetUrl = UriComponentsBuilder
                .fromUriString(DETAIL_URL)
                .queryParam("d_isbn", isbn)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
        return restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, SearchDetailDto.class).getBody();
    }

    private Integer pageInfo(String isbn) {
        RestTemplate restTemplate = new RestTemplate();
        Map obj = restTemplate.getForObject(pageUrl, Map.class, isbn);
        Integer page = null;
        if (Objects.requireNonNull(obj).get("errorCode") == null) //페이지 정보가 있다면
            page = (Integer) ((HashMap) ((HashMap) ((List) obj.get("item")).get(0)).get("subInfo")).get("itemPage");
        return page;
    }

    private HttpEntity<String> getHttpEntity() { //헤더에 인증 정보 추가
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Naver-Client-Id", id);
        httpHeaders.set("X-Naver-Client-Secret", secret);
        return new HttpEntity<>(httpHeaders);
    }

    /**
     * addSearchDetail
     *
     * @param user
     * @param searchReqDto
     * @return addSearchDetailResDto
     */
    @Transactional
    public AddSearchDetailResDto addSearchDetail(User user, SearchReqDto searchReqDto) {
        checkDuplicateBook(user, searchReqDto.getIsbn()); //중복 저장 확인
        validateDate(searchReqDto.getStartDate(), searchReqDto.getEndDate()); //날짜 선후관계 체크
        validateReadPage(searchReqDto.getReadPage(), searchReqDto.getTotPage()); //읽은 쪽수, 전체 쪽수 대소관계 체크

        Book save = bookRepository.save(buildBookFromReq(user, searchReqDto)); //책 저장
        AddSearchDetailResDto addSearchDetailResDto = null;
        if (save.getBookStatus() == BookStatus.DONE) { //저장할 책이 '읽은 책' 이라면
            Characters added = charactersService.addNewCharacters(user); //추가되는 캐릭터 중 가장 키가 큰 캐릭터
            if (added != null)
                addSearchDetailResDto = AddSearchDetailResDto.builder()
                        .id(added.getId())
                        .name(added.getName())
                        .img(added.getImg())
                        .build();
        }
        return addSearchDetailResDto;
    }

    private void checkDuplicateBook(User user, String isbn) {
        if (!(user.getBooks().stream()
                .filter(o -> o.getIsbn().equals(isbn))
                .map(Book::getId).count() == 0))
            throw new DuplicateException("Book id = " + user.getBooks().stream()
                    .filter(o -> o.getIsbn().equals(isbn))
                    .map(Book::getId)
                    .collect(Collectors.toList()).get(0));
    }

    private void validateReadPage(int readPage, Integer totPage) {
        if (totPage != null && readPage > totPage)
            throw new InvalidReqBodyException("page = " + readPage + " > " + totPage);
    }

    private void validateDate(LocalDate start, LocalDate end) {
        if (end.isBefore(start))
            throw new InvalidReqBodyException("date = " + start + " < " + end);
    }

    private Book buildBookFromReq(User user, SearchReqDto searchReqDto) {
        return Book.builder()
                .user(user)
                .title(searchReqDto.getTitle())
                .thumbnail(searchReqDto.getThumbnail())
                .author(searchReqDto.getAuthor())
                .publisher(searchReqDto.getPublisher())
                .description(searchReqDto.getDescription())
                .isbn(searchReqDto.getIsbn())
                .totPage(searchReqDto.getTotPage())
                .url(searchReqDto.getUrl())
                .bookStatus(BookStatus.from(searchReqDto.getBookStatus()))
                .startDate(searchReqDto.getStartDate())
                .endDate(searchReqDto.getEndDate())
                .score(searchReqDto.getScore())
                .readPage(searchReqDto.getReadPage())
                .expectation(searchReqDto.getExpectation())
                .build();
    }
}
