package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.Memo;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos;
import jpa.myunjuk.module.repository.book.BookRepository;
import jpa.myunjuk.module.repository.UserCharacterRepository;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@DisplayName("Bookshelf Memo Service Test")
@Transactional
public class BookshelfMemoServiceTest {

    @Autowired
    BookshelfMemoService bookshelfMemoService;
    @Autowired
    SearchService searchService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserCharacterRepository userCharacterRepository;

    @Test
    @DisplayName("Retrieve all Memos | Success")
    void allMemosSuccess() throws InterruptedException {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        BookshelfDetailDtos.BookshelfMemoReqDto req = getBookshelfMemoReqDto("hello hello hello this is memo.");
        bookshelfMemoService.bookshelfAddMemo(user, req);
        Thread.sleep(2000);
        BookshelfDetailDtos.BookshelfMemoReqDto req2 = getBookshelfMemoReqDto("hello hello hello this is memo2.");
        bookshelfMemoService.bookshelfAddMemo(user, req2);

        List<BookshelfDetailDtos.BookshelfMemoResDto> memos = bookshelfMemoService.bookshelfMemo(user, 42L);
        assertEquals("hello hello hello this is memo2.", memos.get(0).getContent());
    }

    @Test
    @DisplayName("Add Memo | Success")
    void addMemoSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        BookshelfDetailDtos.BookshelfMemoReqDto req = getBookshelfMemoReqDto("hello hello hello this is memo.");
        Memo memo = bookshelfMemoService.bookshelfAddMemo(user, req);

        assertEquals("hello hello hello this is memo.", memo.getContent());
        assertEquals(42L, memo.getBook().getId());
        assertEquals(user, memo.getBook().getUser());
        assertEquals(0, Duration.between(memo.getSaved(), LocalDateTime.now()).getSeconds());
    }

    @Test
    @DisplayName("Update Memo | Success")
    void updateMemoSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        BookshelfDetailDtos.BookshelfMemoReqDto req = getBookshelfMemoReqDto("hello hello hello this is memo.");
        Memo memo = bookshelfMemoService.bookshelfAddMemo(user, req);

        bookshelfMemoService.bookshelfUpdateMemo(user, memo.getId(),
                BookshelfDetailDtos.BookshelfUpdateMemoReqDto.builder()
                        .content("Updated Memo")
                        .build());
        assertEquals("Updated Memo", memo.getContent());
    }

    @Test
    @DisplayName("Delete Memo | Success")
    void deleteMemoSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        BookshelfDetailDtos.BookshelfMemoReqDto req = getBookshelfMemoReqDto("hello hello hello this is memo.");
        Memo memo = bookshelfMemoService.bookshelfAddMemo(user, req);
        int size = bookshelfMemoService.bookshelfMemo(user, 42L).size();

        bookshelfMemoService.bookshelfDeleteMemo(user, memo.getId());
        assertEquals(size - 1, bookshelfMemoService.bookshelfMemo(user, 42L).size());
    }

    private BookshelfDetailDtos.BookshelfMemoReqDto getBookshelfMemoReqDto(String content) {
        return BookshelfDetailDtos.BookshelfMemoReqDto.builder()
                .id(42L)
                .content(content)
                .build();
    }
}
