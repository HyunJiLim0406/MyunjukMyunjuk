package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.BookStatus;
import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.search.*;
import jpa.myunjuk.module.repository.BookRepository;
import jpa.myunjuk.module.repository.CharactersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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
    private final CharactersRepository charactersRepository;
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

    public AddSearchDetailResDto addSearchDetail(User user, SearchReqDto searchReqDto) {
        Book save = bookRepository.save(buildBookFromReq(user, searchReqDto));
        AddSearchDetailResDto addSearchDetailResDto = null;

        if (save.getBookStatus() == BookStatus.DONE) {
            List<Characters> charactersList = charactersRepository.findByHeightLessThanEqual(user.bookHeight());
            Characters added = charactersService.addNewCharacters(user, charactersList);
            if(added!=null)
                addSearchDetailResDto = AddSearchDetailResDto.builder()
                        .id(added.getId())
                        .name(added.getName())
                        .img(added.getImg())
                        .build();
        }
        return addSearchDetailResDto;
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
