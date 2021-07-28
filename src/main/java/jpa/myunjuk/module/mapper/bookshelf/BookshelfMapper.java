package jpa.myunjuk.module.mapper.bookshelf;

import jpa.myunjuk.module.model.domain.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.*;
import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.*;

@Mapper(componentModel = "spring")
public interface BookshelfMapper {
    BookshelfMapper INSTANCE = Mappers.getMapper(BookshelfMapper.class);

    BookshelfInfoDto toDto(Book book);
    DoneBook toDoneDto(Book book);
    ReadingBook toReadingDto(Book book);
    WishBook toWishDto(Book book);
}
