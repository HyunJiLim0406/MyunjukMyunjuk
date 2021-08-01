package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.module.model.domain.BookStatus;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.HomeDto;
import jpa.myunjuk.module.model.dto.search.SearchReqDto;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static jpa.myunjuk.module.model.dto.HomeDto.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Home Service Test")
@Transactional
class HomeServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HomeService homeService;

    @Autowired
    SearchService searchService;

    @Test
    @DisplayName("Home Service | Success")
    void homeServiceSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        SearchReqDto searchReqDto1 = getSearchReqDto("first book", "1234567890 1234567890123", "done", "2021-07-26", 1000, 11);
        SearchReqDto searchReqDto2 = getSearchReqDto("second book", "1234567890 1234567890124", "done", "2021-07-27", 1000, 11);
        searchService.addSearchDetail(user, searchReqDto1);
        searchService.addSearchDetail(user, searchReqDto2);


        int count = (int) user.getBooks().stream()
                .filter(o -> o.getBookStatus() == BookStatus.DONE)
                .count();
        HomeDto result = homeService.home(user, null, null);
        List<Item> itemList = result.getItemList();
        assertEquals(count, result.getSize());
        assertEquals("second book", itemList.get(0).getTitle());
    }

    @Test
    @DisplayName("Home Service | Fail : Invalid Year")
    void homeServiceFailInvalidYear() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        InvalidReqParamException e = assertThrows(InvalidReqParamException.class, () ->
                homeService.home(user, 13, 1));
        assertEquals("year = 13", e.getMessage());
    }

    @Test
    @DisplayName("Home Service | Fail : Invalid Month")
    void homeServiceFailInvalidMonth() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        InvalidReqParamException e = assertThrows(InvalidReqParamException.class, () ->
                homeService.home(user, 2014, 13));
        assertEquals("month = 13", e.getMessage());
    }

    private SearchReqDto getSearchReqDto(String title, String isbn, String bookStatus, String endDate, Integer totPage, int readPage) {
        return SearchReqDto.builder()
                .title(title)
                .url("http://blahblah")
                .isbn(isbn)
                .bookStatus(bookStatus)
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse(endDate))
                .score(5)
                .totPage(totPage)
                .readPage(readPage)
                .build();
    }
}