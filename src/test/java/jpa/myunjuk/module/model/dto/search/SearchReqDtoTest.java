package jpa.myunjuk.module.model.dto.search;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SearchReqDto Validation Test")
class SearchReqDtoTest {

    public static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    public static Validator validator = factory.getValidator();

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Search Request | Fail : Invalid isbn")
    void invalidIsbn() throws Exception {
        SearchReqDto req = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("123 123")
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(5)
                .readPage(1)
                .build();
        Set<ConstraintViolation<SearchReqDto>> violations = validator.validate(req);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("\"^.{10}\\s.{13}$\"와 일치해야 합니다");
        });
    }

    @Test
    @DisplayName("Search Request | Fail : Invalid bookStatus")
    void invalidBookStatus() throws Exception {
        SearchReqDto req = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("wrong")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(5)
                .readPage(1)
                .build();
        Set<ConstraintViolation<SearchReqDto>> violations = validator.validate(req);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("Invalid value. This is not permitted.");
        });
    }

    @Test
    @DisplayName("Search Request | Fail : Invalid Future Date")
    void invalidFutureDate() throws Exception {
        SearchReqDto req = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-28"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(5)
                .readPage(1)
                .build();
        Set<ConstraintViolation<SearchReqDto>> violations = validator.validate(req);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("과거 또는 현재의 날짜여야 합니다");
        });
    }

    @Test
    @DisplayName("Search Request | Fail : Invalid Date Format")
    void invalidDateFormat() throws Exception {
        DateTimeParseException e = assertThrows(DateTimeParseException.class, () ->
                SearchReqDto.builder()
                        .title("Good Omens")
                        .url("http://blahblah")
                        .isbn("1234567890 1234567890123")
                        .bookStatus("done")
                        .startDate(LocalDate.parse("20210726"))
                        .endDate(LocalDate.parse("2021-07-26"))
                        .score(5)
                        .readPage(1)
                        .build());
        assertEquals("Text '20210726' could not be parsed at index 0", e.getMessage());
    }

    @Test
    @DisplayName("Search Request | Fail : Invalid Max score")
    void invalidMaxScore() throws Exception {
        SearchReqDto req = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(15)
                .readPage(1)
                .build();
        Set<ConstraintViolation<SearchReqDto>> violations = validator.validate(req);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("10 이하여야 합니다");
        });
    }

    @Test
    @DisplayName("Search Request | Fail : Invalid Min score")
    void invalidMinScore() throws Exception {
        SearchReqDto req = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(-1)
                .readPage(1)
                .build();
        Set<ConstraintViolation<SearchReqDto>> violations = validator.validate(req);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("0 이상이어야 합니다");
        });
    }

    @Test
    @DisplayName("Search Request | Fail : Invalid readPage")
    void invalidReadPage() throws Exception {
        SearchReqDto req = SearchReqDto.builder()
                .title("Good Omens")
                .url("http://blahblah")
                .isbn("1234567890 1234567890123")
                .bookStatus("done")
                .startDate(LocalDate.parse("2021-07-26"))
                .endDate(LocalDate.parse("2021-07-26"))
                .score(5)
                .readPage(-1)
                .build();
        Set<ConstraintViolation<SearchReqDto>> violations = validator.validate(req);
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("0 이상이어야 합니다");
        });
    }
}