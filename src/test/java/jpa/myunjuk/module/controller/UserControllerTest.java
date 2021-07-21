package jpa.myunjuk.module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpa.myunjuk.module.model.dto.user.UserSignInReqDto;
import jpa.myunjuk.module.model.dto.user.UserSignUpReqDto;
import jpa.myunjuk.module.repository.UserRepository;
import jpa.myunjuk.module.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@DisplayName("User Controller Test")
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName("User Sign-Up | Success")
    void signUpSuccess() throws Exception {
        UserSignUpReqDto userSignUpReqDto = UserSignUpReqDto.builder()
                .email("new@new.com")
                .password("1234")
                .nickname("hello")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignUpReqDto);
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("User Sign-Up | Fail : Duplicate User")
    void signUpFailDuplicate() throws Exception {
        UserSignUpReqDto userSignUpReqDto = UserSignUpReqDto.builder()
                .email("test@test.com")
                .password("1234")
                .nickname("hello")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignUpReqDto);

        Map<String, String> error = new HashMap<>();
        error.put("DuplicateUserException", "email = test@test.com");
        String response = objectMapper.writeValueAsString(error);
        mockMvc.perform(post("/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("User Sign-In | Success")
    void signInSuccess() throws Exception {
        UserSignInReqDto userSignInReqDto = UserSignInReqDto.builder()
                .email("test@test.com")
                .password("1234")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignInReqDto);
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User Sign-In | Fail : Wrong Email")
    void singInFailEmail() throws Exception {
        UserSignInReqDto userSignInReqDto = UserSignInReqDto.builder()
                .email("wrong@test.com")
                .password("1234")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignInReqDto);

        Map<String, String> error = new HashMap<>();
        error.put("NoSuchDataException", "email = wrong@test.com");
        String response = objectMapper.writeValueAsString(error);
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("User Sign-In | Fail : Wrong Password")
    void singInFailPassword() throws Exception {
        UserSignInReqDto userSignInReqDto = UserSignInReqDto.builder()
                .email("test@test.com")
                .password("wrong")
                .build();
        String jsonString = objectMapper.writeValueAsString(userSignInReqDto);

        Map<String, String> error = new HashMap<>();
        error.put("NoSuchDataException", "password = wrong");
        String response = objectMapper.writeValueAsString(error);
        mockMvc.perform(post("/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }
}