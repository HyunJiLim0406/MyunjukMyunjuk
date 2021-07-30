package jpa.myunjuk.module.model.dto.bookshelf;

import jpa.myunjuk.infra.annotation.Enum;
import jpa.myunjuk.module.model.domain.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookshelfDetailDtos {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookshelfUpdateReqDto{

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

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookshelfInfoDto {

        private Long id;
        private String description;
        private String publisher;
        private String isbn;
        private Integer totPage;
        private String url;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class BookshelfInfoUpdateReqDto {

        private String title;
        private String author;
        private String publisher;
        private String isbn;
        private Integer totPage;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class BookshelfMemoReqDto {

        private Long id;
        private String content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookshelfMemoResDto {
        private Long id;
        private String content;
        private LocalDateTime saved;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class BookshelfUpdateMemoReqDto {
        private String content;
    }
}
