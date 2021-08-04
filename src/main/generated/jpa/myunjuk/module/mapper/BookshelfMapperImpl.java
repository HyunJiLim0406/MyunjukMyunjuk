package jpa.myunjuk.module.mapper;

import javax.annotation.processing.Generated;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.BookshelfInfoDto;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.BookshelfInfoDto.BookshelfInfoDtoBuilder;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.BookshelfMemoResDto;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.BookshelfMemoResDto.BookshelfMemoResDtoBuilder;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.DoneBook;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.DoneBook.DoneBookBuilder;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.ReadingBook;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.ReadingBook.ReadingBookBuilder;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.WishBook;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.WishBook.WishBookBuilder;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-08-04T15:28:10+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 14.0.1 (Oracle Corporation)"
)
@Component
public class BookshelfMapperImpl implements BookshelfMapper {

    @Override
    public WishBook toWishBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        WishBookBuilder wishBook = WishBook.builder();

        wishBook.id( book.getId() );
        wishBook.title( book.getTitle() );
        wishBook.author( book.getAuthor() );
        wishBook.thumbnail( book.getThumbnail() );
        wishBook.bookStatus( book.getBookStatus() );
        wishBook.totPage( book.getTotPage() );
        if ( book.getScore() != null ) {
            wishBook.score( book.getScore() );
        }
        wishBook.expectation( book.getExpectation() );

        return wishBook.build();
    }

    @Override
    public BookshelfInfoDto toBookshelfInfoDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookshelfInfoDtoBuilder bookshelfInfoDto = BookshelfInfoDto.builder();

        bookshelfInfoDto.id( book.getId() );
        bookshelfInfoDto.description( book.getDescription() );
        bookshelfInfoDto.publisher( book.getPublisher() );
        bookshelfInfoDto.isbn( book.getIsbn() );
        bookshelfInfoDto.totPage( book.getTotPage() );
        bookshelfInfoDto.url( book.getUrl() );

        return bookshelfInfoDto.build();
    }

    @Override
    public DoneBook toDoneBook(Book book) {
        if ( book == null ) {
            return null;
        }

        DoneBookBuilder doneBook = DoneBook.builder();

        doneBook.id( book.getId() );
        doneBook.title( book.getTitle() );
        doneBook.author( book.getAuthor() );
        doneBook.thumbnail( book.getThumbnail() );
        doneBook.bookStatus( book.getBookStatus() );
        doneBook.startDate( book.getStartDate() );
        doneBook.endDate( book.getEndDate() );
        if ( book.getScore() != null ) {
            doneBook.score( book.getScore() );
        }

        return doneBook.build();
    }

    @Override
    public ReadingBook toReadingBook(Book book) {
        if ( book == null ) {
            return null;
        }

        ReadingBookBuilder readingBook = ReadingBook.builder();

        readingBook.id( book.getId() );
        readingBook.title( book.getTitle() );
        readingBook.author( book.getAuthor() );
        readingBook.thumbnail( book.getThumbnail() );
        readingBook.bookStatus( book.getBookStatus() );
        readingBook.startDate( book.getStartDate() );
        if ( book.getReadPage() != null ) {
            readingBook.readPage( book.getReadPage() );
        }
        readingBook.totPage( book.getTotPage() );

        return readingBook.build();
    }

    @Override
    public WishBook toWishBook(Book book) {
        if ( book == null ) {
            return null;
        }

        WishBookBuilder wishBook = WishBook.builder();

        wishBook.id( book.getId() );
        wishBook.title( book.getTitle() );
        wishBook.author( book.getAuthor() );
        wishBook.thumbnail( book.getThumbnail() );
        wishBook.bookStatus( book.getBookStatus() );
        wishBook.totPage( book.getTotPage() );
        if ( book.getScore() != null ) {
            wishBook.score( book.getScore() );
        }
        wishBook.expectation( book.getExpectation() );

        return wishBook.build();
    }

    @Override
    public BookshelfMemoResDto toBookshelfMemoResDto(Memo memo) {
        if ( memo == null ) {
            return null;
        }

        BookshelfMemoResDtoBuilder bookshelfMemoResDto = BookshelfMemoResDto.builder();

        bookshelfMemoResDto.id( memo.getId() );
        bookshelfMemoResDto.content( memo.getContent() );
        bookshelfMemoResDto.saved( memo.getSaved() );

        return bookshelfMemoResDto.build();
    }
}
