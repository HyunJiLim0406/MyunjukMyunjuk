package jpa.myunjuk.module.mapper;

import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.*;
import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.*;

@Mapper(componentModel = "spring")
public interface BookshelfMapper {
    BookshelfMapper INSTANCE = Mappers.getMapper(BookshelfMapper.class);

    WishBook toWishBookDto(Book book);
    BookshelfMemoResDto toBookshelfMemoResDto(Memo memo);
    BookshelfInfoDto toBookshelfInfoDto(Book book);
    DoneBook toDoneBook(Book book);
    ReadingBook toReadingBook(Book book);
    WishBook toWishBook(Book book);
}
