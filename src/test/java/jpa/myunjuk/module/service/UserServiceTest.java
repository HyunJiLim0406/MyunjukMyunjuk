package jpa.myunjuk.module.service;

import io.jsonwebtoken.Jwts;
import jpa.myunjuk.infra.exception.DuplicateUserException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.JwtDto;
import jpa.myunjuk.module.model.dto.user.UserDto;
import jpa.myunjuk.module.model.dto.user.UserJoinReqDto;
import jpa.myunjuk.module.model.dto.user.UserLogInReqDto;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("User Service Test")
@Transactional
class UserServiceTest {

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    void init() {
        userService.join(createUser("h@gmail.com", "1234", "hello"));
    }

    @Test
    @DisplayName("User Join | Success")
    void joinSuccess() {
        assertTrue(userRepository.findByEmail("h@gmail.com").isPresent());
    }

    @Test
    @DisplayName("User Join | Fail : Duplicate User")
    void duplicateUser() throws Exception {
        assertThrows(DuplicateUserException.class, () -> {
            UserJoinReqDto duplicateUser = createUser("h@gmail.com", "1234", "hello");
            userService.join(duplicateUser);
        });
    }

    @Test
    @DisplayName("User Log-In | Fail : Wrong Email")
    void wrongEmail() throws Exception {
        assertThrows(NoSuchDataException.class, () -> {
            userService.logIn(UserLogInReqDto.builder()
                    .email("h@g.c")
                    .password("1234")
                    .build());
        });
    }

    @Test
    @DisplayName("User Log-In | Fail : Wrong Password")
    void wrongPassword() throws Exception {
        assertThrows(NoSuchDataException.class, () -> {
            userService.logIn(UserLogInReqDto.builder()
                    .email("h@gmail.com")
                    .password("12314")
                    .build());
        });
    }

    @Test
    @DisplayName("User Log-Out | Success")
    void logOutSuccess() {
        User user = userRepository.findByEmail("h@gmail.com").get();
        user.setRefreshTokenValue("1111");
        userService.logOut(user);
        //assertNull(user.getRefreshTokenValue());
    }

    private UserJoinReqDto createUser(String email, String password, String nickname) {
        return UserJoinReqDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}