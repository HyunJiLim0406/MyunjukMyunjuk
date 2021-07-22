package jpa.myunjuk.module.model.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static jpa.myunjuk.module.model.dto.JwtDtos.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jwt Dtos Validation Test")
class JwtDtosTest {

    public static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    public static Validator validator = factory.getValidator();

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Jwt Dtos Request | Fail : Email Null")
    void emptyEmail(){
        JwtRefreshReqDto req = JwtRefreshReqDto.builder()
                .refreshToken("12345678")
                .build();
        Set<ConstraintViolation<JwtRefreshReqDto>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("Jwt Dtos Request | Fail : Invalid Email Format")
    void invalidEmail(){
        JwtRefreshReqDto req = JwtRefreshReqDto.builder()
                .email("wrong_format")
                .refreshToken("12345678")
                .build();
        Set<ConstraintViolation<JwtRefreshReqDto>> violations = validator.validate(req);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("올바른 형식의 이메일 주소여야 합니다");
        });
    }

    @Test
    @DisplayName("Jwt Dtos Request | Fail : refreshToken Null")
    void emptyPassword(){
        JwtRefreshReqDto req = JwtRefreshReqDto.builder()
                .email("test@test.com")
                .build();
        Set<ConstraintViolation<JwtRefreshReqDto>> violations = validator.validate(req);
        assertThat(violations).isEmpty();
    }
}