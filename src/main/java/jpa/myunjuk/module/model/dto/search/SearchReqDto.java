package jpa.myunjuk.module.model.dto.search;

import jpa.myunjuk.infra.annotation.Enum;
import jpa.myunjuk.module.model.domain.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchReqDto {

    @NotNull
    private String title;

    @NotNull
    private String url;
    private String thumbnail;
    private String author;
    private String publisher;

    @NotNull
    @Pattern(regexp = "^.{10}\\s.{13}$")
    private String isbn;
    private String description;
    private Integer totPage;

    @NotNull
    @Enum(enumClass = BookStatus.class, ignoreCase = true)
    private String bookStatus;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate endDate;

    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private Integer score;

    @NotNull
    @PositiveOrZero
    private Integer readPage;

    private String expectation;
}
