package jpa.myunjuk.module.mapper.bookshelf;

import jpa.myunjuk.module.mapper.GenericMapper;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailResDtos;
import org.mapstruct.Mapper;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailResDtos.*;

@Mapper(componentModel = "spring")
public interface BookshelfDetailMapper extends GenericMapper<BookshelfDetailResDtos, Book> {

    DetailDoneBook toDetailDoneBookDto(Book e);
    DetailReadingBook toDetailReadingBookDto(Book e);
    DetailWishBook toDetailWishBookDto(Book e);
}