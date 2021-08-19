package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static jpa.myunjuk.module.model.dto.UserDtos.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Profile Service Test")
@Transactional
class ProfileServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileService profileService;

    @Test
    @DisplayName("Profile | Success")
    void profileSuccess() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        UserProfileDto profile = profileService.profile(user);
        assertEquals("hello", profile.getNickname());
        assertNull(profile.getImg());
    }

    @Test
    @DisplayName("User info | Success")
    void infoSuccess() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        UserInfoDto info = profileService.info(user);
        assertEquals("test@test.com", info.getEmail());
        assertEquals("hello", info.getNickname());
    }

    @Test
    @DisplayName("Update img | Success")
    void updateImgSuccess() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        user.updateUserImg("tmp_tmp");
        userRepository.save(user);

        assertNotNull(user.getImg());
        profileService.updateImg(user, null);
        assertNull(user.getImg());
    }

    @Test
    @DisplayName("Update nickname | Success")
    void updateNickname() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        profileService.updateNickname(user, new UserNickNameReqDto("new nickname"));
        assertEquals("new nickname", user.getNickname());
    }

    @Test
    @DisplayName("User Sign-Out | Success")
    void signOutSuccess() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);
        assertNotNull(user.getRefreshTokenValue());
        profileService.signOut(user);
        assertNull(user.getRefreshTokenValue());
    }
}