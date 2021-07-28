package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.AccessDeniedException;
import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Bookshelf Service Test")
@Transactional
class BookshelfServiceTest {

    @Autowired
    BookshelfService bookshelfService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Retrieve all from Bookshelf | Success")
    void allBookshelfSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        InvalidReqParamException e = assertThrows(InvalidReqParamException.class, () ->
                bookshelfService.bookshelf(user, "wrong"));

        assertEquals("BookStatus = wrong", e.getMessage());
        assertEquals(user.getBooks().size(), bookshelfService.bookshelf(user, null).size());
    }
}