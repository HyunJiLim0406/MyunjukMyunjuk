package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.*;
import jpa.myunjuk.module.model.dto.search.SearchReqDto;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import jpa.myunjuk.module.repository.CharactersRepository;
import jpa.myunjuk.module.repository.UserRepository;
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

        SearchReqDto searchReqDto = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(5)
                .totPage(2000)
                .readPage(1)
                .build();
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

        SearchReqDto searchReqDto = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(5)
                .totPage(null)
                .readPage(1)
                .build();
        assertNull(searchService.addSearchDetail(user, searchReqDto));
        assertEquals(bookCnt + 1, user.getBooks().size());
    }

    @Test
    @DisplayName("Add Search Detail | Success : READING")
    void addSearchDetailSuccessReading() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        int bookCnt = user.getBooks().size();

        SearchReqDto searchReqDto = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("reading")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(5)
                .totPage(2000)
                .readPage(1)
                .build();
        assertNull(searchService.addSearchDetail(user, searchReqDto));
        assertEquals(bookCnt + 1, user.getBooks().size());
    }
}