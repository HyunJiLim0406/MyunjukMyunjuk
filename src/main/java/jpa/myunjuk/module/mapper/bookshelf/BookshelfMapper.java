package jpa.myunjuk.module.mapper.bookshelf;

import jpa.myunjuk.module.mapper.GenericMapper;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos;
import org.mapstruct.Mapper;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.*;

@Mapper(componentModel = "spring")
public interface BookshelfMapper extends GenericMapper<BookshelfResDtos, Book> {

    DoneBook toDoneBookDto(Book e);
    ReadingBook toReadingBookDto(Book e);
    WishBook toWishBookDto(Book e);
}