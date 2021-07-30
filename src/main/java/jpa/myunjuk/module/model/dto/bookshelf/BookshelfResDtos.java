package jpa.myunjuk.module.model.dto.bookshelf;

import jpa.myunjuk.module.model.domain.BookStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class BookshelfResDtos {

    @Getter
    public static class DoneBook extends BookshelfBasic{

        private LocalDate startDate;
        private LocalDate endDate;
        private int score;

        @Builder
        public DoneBook(Long id, String title, String author, String thumbnail, BookStatus bookStatus,
                        LocalDate startDate, LocalDate endDate, int score) {
            super(id, title, author, thumbnail, bookStatus);
            this.startDate = startDate;
            this.endDate = endDate;
            this.score = score;
        }
    }

    @Getter
    public static class ReadingBook extends BookshelfBasic{

        private LocalDate startDate;
        private int readPage;
        private Integer totPage;

        @Builder
        public ReadingBook(Long id, String title, String author, String thumbnail, BookStatus bookStatus,
                           LocalDate startDate, int readPage, Integer totPage) {
            super(id, title, author, thumbnail, bookStatus);
            this.startDate = startDate;
            this.readPage = readPage;
            this.totPage = totPage;
        }
    }

    @Getter
    public static class WishBook extends BookshelfBasic{

        private Integer totPage;
        private int score;
        private String expectation;

        @Builder
        public WishBook(Long id, String title, String author, String thumbnail, BookStatus bookStatus,
                        Integer totPage, int score, String expectation) {
            super(id, title, author, thumbnail, bookStatus);
            this.totPage = totPage;
            this.score = score;
            this.expectation = expectation;
        }
    }
}
