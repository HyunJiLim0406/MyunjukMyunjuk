package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.AccessDeniedException;
import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.model.domain.*;
import jpa.myunjuk.module.repository.book.BookRepository;
import jpa.myunjuk.module.repository.UserCharacterRepository;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Bookshelf Service Test")
@Transactional
class BookshelfServiceTest {

    @Autowired
    BookshelfService bookshelfService;
    @Autowired
    SearchService searchService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserCharacterRepository userCharacterRepository;

    @Test
    @DisplayName("Retrieve all from Bookshelf | Success")
    void allBookshelfSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        assertEquals(user.getBooks().size(), bookshelfService.bookshelf(user, null).size());
    }


    @Test
    @DisplayName("Retrieve all from Bookshelf | Fail : Invalid Param")
    void allBookshelfFailInvalidParam() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        InvalidReqParamException e = assertThrows(InvalidReqParamException.class, () ->
                bookshelfService.bookshelf(user, "wrong"));

        assertEquals("BookStatus = wrong", e.getMessage());
    }

    @Test
    @DisplayName("Update book | Success : to Done")
    void updateBookSuccessToDone() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        Book book = bookRepository.findById(72L).orElse(null);
        assertNotNull(book);
        assertNotNull(user);

        int size = user.getUserCharacters().size();
        BookshelfUpdateReqDto req = BookshelfUpdateReqDto.builder()
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-20"))
                .endDate(LocalDate.parse("2021-07-23"))
                .score(10)
                .readPage(1)
                .build();

        assertEquals(BookStatus.WISH, book.getBookStatus());
        bookshelfService.bookshelfUpdate(user, 72L, req);
        assertEquals(BookStatus.DONE, book.getBookStatus());
        assertTrue(user.getUserCharacters().size() > size);
    }

    @Test
    @DisplayName("Update book | Success : from Done")
    void updateBookSuccessFromDone() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        Book book = bookRepository.findById(67L).orElse(null);
        assertNotNull(book);
        assertNotNull(user);

        int size = user.getUserCharacters().size();
        System.out.println("user.bookHeight() = " + user.getBookHeight());
        BookshelfUpdateReqDto req = BookshelfUpdateReqDto.builder()
                .bookStatus("wish")
                .startDate(LocalDate.parse("2021-07-20"))
                .endDate(LocalDate.parse("2021-07-23"))
                .score(10)
                .readPage(1)
                .build();

        assertEquals(BookStatus.DONE, book.getBookStatus());
        bookshelfService.bookshelfUpdate(user, 67L, req);
        assertTrue(user.getUserCharacters().size() < size);
    }

    @Test
    @DisplayName("Delete book | Success")
    void deleteBookSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        Book book = bookRepository.findById(67L).orElse(null);
        assertNotNull(book);
        assertNotNull(user);

        int size = user.getBooks().size();
        bookshelfService.bookshelfDelete(user, 67L);
        assertNull(bookRepository.findById(67L).orElse(null));
        assertTrue(user.getBooks().size() < size);
    }

    @Test
    @DisplayName("Bookshelf Detail | Fail : No Such Data")
    void bookshelfFailNoSuchData() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        NoSuchDataException e = assertThrows(NoSuchDataException.class, () ->
                bookshelfService.bookshelfInfo(user, 1000L));

        assertEquals("Book id = 1000", e.getMessage());
    }

    @Test
    @DisplayName("Bookshelf Detail | Fail : Access Deny")
    void bookshelfFailAccessDenied() throws Exception {
        User user = userRepository.findByEmail("test2@test.com").orElse(null);
        assertNotNull(user);

        AccessDeniedException e = assertThrows(AccessDeniedException.class, () ->
                bookshelfService.bookshelfInfo(user, 70L));

        assertEquals("Book id = 70", e.getMessage());
    }

    @Test
    @DisplayName("Retrieve Book Info | Success")
    void bookInfoSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        assertEquals("1155351312 9791155351314", bookshelfService.bookshelfInfo(user, 42L).getIsbn());
    }
}