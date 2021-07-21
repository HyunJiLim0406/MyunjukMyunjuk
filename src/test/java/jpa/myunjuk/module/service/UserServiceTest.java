package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("User Service Test")
@Transactional
class UserServiceTest {

    @Autowired private UserRepository userRepository;
    @Autowired private UserService userService;

    @Test
    @DisplayName("User Sign-Out | Success")
    void signOutSuccess() throws Exception {
        User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);
        userService.signOut(user);
        assertNull(user.getRefreshTokenValue());
    }
}