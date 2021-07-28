package jpa.myunjuk.module.mapper.bookshelf;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.BookStatus;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailResDtos;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailResDtos.DetailDoneBook;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailResDtos.DetailReadingBook;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailResDtos.DetailWishBook;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-28T20:10:12+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class BookshelfDetailMapperImpl implements BookshelfDetailMapper {

    @Override
    public BookshelfDetailResDtos toDto(Book e) {
        if ( e == null ) {
            return null;
        }

        BookshelfDetailResDtos bookshelfDetailResDtos = new BookshelfDetailResDtos();

        return bookshelfDetailResDtos;
    }

    @Override
    public Book toEntity(BookshelfDetailResDtos d) {
        if ( d == null ) {
            return null;
        }

        Long id = null;
        User user = null;
        String title = null;
        String thumbnail = null;
        String author = null;
        String publisher = null;
        String description = null;
        String isbn = null;
        Integer totPage = null;
        String url = null;
        BookStatus bookStatus = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        Integer score = null;
        Integer readPage = null;
        String expectation = null;

        Book book = new Book( id, user, title, thumbnail, author, publisher, description, isbn, totPage, url, bookStatus, startDate, endDate, score, readPage, expectation );

        return book;
    }

    @Override
    public void updateFromDto(BookshelfDetailResDtos dto, Book entity) {
        if ( dto == null ) {
            return;
        }
    }

    @Override
    public DetailDoneBook toDetailDoneBookDto(Book e) {
        if ( e == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String thumbnail = null;
        String author = null;
        BookStatus bookStatus = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        int score = 0;

        DetailDoneBook detailDoneBook = new DetailDoneBook( id, title, thumbnail, author, bookStatus, startDate, endDate, score );

        return detailDoneBook;
    }

    @Override
    public DetailReadingBook toDetailReadingBookDto(Book e) {
        if ( e == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String thumbnail = null;
        String author = null;
        BookStatus bookStatus = null;
        LocalDate startDate = null;
        int readPage = 0;

        DetailReadingBook detailReadingBook = new DetailReadingBook( id, title, thumbnail, author, bookStatus, startDate, readPage );

        return detailReadingBook;
    }

    @Override
    public DetailWishBook toDetailWishBookDto(Book e) {
        if ( e == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String thumbnail = null;
        String author = null;
        BookStatus bookStatus = null;
        int score = 0;
        String expectation = null;

        DetailWishBook detailWishBook = new DetailWishBook( id, title, thumbnail, author, bookStatus, score, expectation );

        return detailWishBook;
    }
}
