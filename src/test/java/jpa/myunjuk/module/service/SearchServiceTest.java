package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.DuplicateException;
import jpa.myunjuk.infra.exception.InvalidReqBodyException;
import jpa.myunjuk.module.model.domain.*;
import jpa.myunjuk.module.model.dto.search.SearchReqDto;
import jpa.myunjuk.module.repository.CharactersRepository;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Search Service Test")
@Transactional
class SearchServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SearchService searchService;

    @Autowired
    CharactersRepository charactersRepository;

    @Test
    @DisplayName("Add Search Detail | Success")
    void addSearchDetailSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        int bookCnt = user.getBooks().size();
        int characterCnt = user.getUserCharacters().size();

        SearchReqDto searchReqDto = getSearchReqDto("done", "2021-07-26", 2000, 1);
        assertNotNull(searchService.addSearchDetail(user, searchReqDto));
        assertEquals(bookCnt + 1, user.getBooks().size());
        assertTrue(characterCnt == 8 || user.getUserCharacters().size() > characterCnt);
    }

    @Test
    @DisplayName("Add Search Detail | Success : Null Page")
    void addSearchDetailSuccessNullPage() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        int bookCnt = user.getBooks().size();

        SearchReqDto searchReqDto = getSearchReqDto("done", "2021-07-26", null, 1);
        assertNull(searchService.addSearchDetail(user, searchReqDto));
        assertEquals(bookCnt + 1, user.getBooks().size());
    }

    @Test
    @DisplayName("Add Search Detail | Success : READING")
    void addSearchDetailSuccessReading() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        int bookCnt = user.getBooks().size();

        SearchReqDto searchReqDto = getSearchReqDto("reading", "2021-07-26", 2000, 1);
        assertNull(searchService.addSearchDetail(user, searchReqDto));
        assertEquals(bookCnt + 1, user.getBooks().size());
    }

    @Test
    @DisplayName("Add Search Detail | Fail : Invalid Date")
    void addSearchDetailFailInvalidDate() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        SearchReqDto searchReqDto = getSearchReqDto("done", "2021-07-20", 2000, 1);

        InvalidReqBodyException e = assertThrows(InvalidReqBodyException.class, () ->
                searchService.addSearchDetail(user, searchReqDto));
        assertEquals("date = 2021-07-26 < 2021-07-20", e.getMessage());
    }

    @Test
    @DisplayName("Add Search Detail | Fail : Invalid Page")
    void addSearchDetailFailInvalidPage() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        SearchReqDto searchReqDto = getSearchReqDto("done", "2021-07-26", 10, 1000);

        InvalidReqBodyException e = assertThrows(InvalidReqBodyException.class, () ->
                searchService.addSearchDetail(user, searchReqDto));
        assertEquals("page = 1000 > 10", e.getMessage());
    }

    @Test
    @DisplayName("Add Search Detail | Fail : Duplicate Book")
    void addSearchDetailFailDuplicateBook() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        SearchReqDto searchReqDto1 = getSearchReqDto("done", "2021-07-26", 1000, 11);
        SearchReqDto searchReqDto2 = getSearchReqDto("reading", "2021-07-26", 1001, 10);

        DuplicateException e = assertThrows(DuplicateException.class, () -> {
            searchService.addSearchDetail(user, searchReqDto1);
            searchService.addSearchDetail(user, searchReqDto2);
        });
        assertEquals("isbn = 1234567890 1234567890123", e.getMessage());
    }

    private SearchReqDto getSearchReqDto(String bookStatus, String endDate, Integer totPage, int readPage) {
        return SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus(bookStatus)
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse(endDate))
                .score(5)
                .totPage(totPage)
                .readPage(readPage)
                .build();
    }
}