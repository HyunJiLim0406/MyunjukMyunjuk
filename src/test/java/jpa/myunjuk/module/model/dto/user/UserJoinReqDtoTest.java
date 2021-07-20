package jpa.myunjuk.module.model.dto.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("User Join Validation Test")
class UserJoinReqDtoTest {

    public static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    public static Validator validator = factory.getValidator();

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("User Join Request | Fail : Email Null")
    void emptyEmail(){
        UserJoinReqDto user = UserJoinReqDto.builder()
                .password("1234")
                .nickname("hello")
                .build();
        Set<ConstraintViolation<UserJoinReqDto>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User Join Request | Fail : Invalid Email Format")
    void invalidEmail(){
        UserJoinReqDto user = UserJoinReqDto.builder()
                .email("wrong_format")
                .password("1234")
                .nickname("hello")
                .build();
        Set<ConstraintViolation<UserJoinReqDto>> violations = validator.validate(user);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("올바른 형식의 이메일 주소여야 합니다");
        });
    }

    @Test
    @DisplayName("User Join Request | Fail : Password Null")
    void emptyPassword(){
        UserJoinReqDto user = UserJoinReqDto.builder()
                .email("h@gmail.com")
                .nickname("hello")
                .build();
        Set<ConstraintViolation<UserJoinReqDto>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User Join Request | Fail : Nickname Null")
    void emptyNickname(){
        UserJoinReqDto user = UserJoinReqDto.builder()
                .email("h@gmail.com")
                .password("1234")
                .build();
        Set<ConstraintViolation<UserJoinReqDto>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }
}