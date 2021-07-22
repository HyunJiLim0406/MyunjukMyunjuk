package jpa.myunjuk.module.model.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static jpa.myunjuk.module.model.dto.UserDtos.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User Dtos Validation Test")
class UserDtosTest {

    public static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    public static Validator validator = factory.getValidator();

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("User Sign-Up/In Request | Fail : Email Null")
    void emptyEmail(){
        UserSignUpReqDto user = UserSignUpReqDto.builder()
                .password("1234")
                .nickname("hello")
                .build();
        Set<ConstraintViolation<UserSignUpReqDto>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User Sign-Up/In Request | Fail : Invalid Email Format")
    void invalidEmail(){
        UserSignUpReqDto user = UserSignUpReqDto.builder()
                .email("wrong_format")
                .password("1234")
                .nickname("hello")
                .build();
        Set<ConstraintViolation<UserSignUpReqDto>> violations = validator.validate(user);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("올바른 형식의 이메일 주소여야 합니다");
        });
    }

    @Test
    @DisplayName("User Sign-Up/In Request | Fail : Password Null")
    void emptyPassword(){
        UserSignUpReqDto user = UserSignUpReqDto.builder()
                .email("h@gmail.com")
                .nickname("hello")
                .build();
        Set<ConstraintViolation<UserSignUpReqDto>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User Sign-Up Request | Fail : Nickname Null")
    void emptyNickname(){
        UserSignUpReqDto user = UserSignUpReqDto.builder()
                .email("h@gmail.com")
                .password("1234")
                .build();
        Set<ConstraintViolation<UserSignUpReqDto>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }
}