package jpa.myunjuk.module.mapper.bookshelf;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.BookStatus;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.DoneBook;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.ReadingBook;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.WishBook;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-28T20:10:11+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class BookshelfMapperImpl implements BookshelfMapper {

    @Override
    public BookshelfResDtos toDto(Book e) {
        if ( e == null ) {
            return null;
        }

        BookshelfResDtos bookshelfResDtos = new BookshelfResDtos();

        return bookshelfResDtos;
    }

    @Override
    public Book toEntity(BookshelfResDtos d) {
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
    public void updateFromDto(BookshelfResDtos dto, Book entity) {
        if ( dto == null ) {
            return;
        }
    }

    @Override
    public DoneBook toDoneBookDto(Book e) {
        if ( e == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String author = null;
        String thumbnail = null;
        BookStatus bookStatus = null;
        LocalDate startDate = null;
        LocalDate endDate = null;
        int score = 0;

        DoneBook doneBook = new DoneBook( id, title, author, thumbnail, bookStatus, startDate, endDate, score );

        return doneBook;
    }

    @Override
    public ReadingBook toReadingBookDto(Book e) {
        if ( e == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String author = null;
        String thumbnail = null;
        BookStatus bookStatus = null;
        LocalDate startDate = null;
        int readPage = 0;
        Integer totPage = null;

        ReadingBook readingBook = new ReadingBook( id, title, author, thumbnail, bookStatus, startDate, readPage, totPage );

        return readingBook;
    }

    @Override
    public WishBook toWishBookDto(Book e) {
        if ( e == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String author = null;
        String thumbnail = null;
        BookStatus bookStatus = null;
        Integer totPage = null;
        int score = 0;
        String expectation = null;

        WishBook wishBook = new WishBook( id, title, author, thumbnail, bookStatus, totPage, score, expectation );

        return wishBook;
    }
}
