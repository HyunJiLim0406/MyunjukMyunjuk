package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static jpa.myunjuk.module.model.dto.UserDtos.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("User Service Test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("User Sign-Up | Success")
    void singUpSuccess() throws Exception {
        UserSignUpReqDto userSignUpReqDto = UserSignUpReqDto.builder()
                .email("new@new.com")
                .password("1234")
                .nickname("hello")
                .build();
        User user = userService.signUp(userSignUpReqDto);

        //양방향 제대로 됐는지 확인
        assertEquals(user.getUserCharacters().size(), 1);
        assertTrue(user.getUserCharacters().get(0).isRepresentation());
    }

    @Test
    @DisplayName("User Sign-Out | Success")
    void signOutSuccess() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        assertNotNull(user.getRefreshTokenValue());
        userService.signOut(user);
        assertNull(user.getRefreshTokenValue());
    }
}