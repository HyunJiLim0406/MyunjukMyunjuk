package jpa.myunjuk.module.mapper.bookshelf;

import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.*;
import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfResDtos.*;

@Mapper(componentModel = "spring")
public interface BookshelfMapper {
    BookshelfMapper INSTANCE = Mappers.getMapper(BookshelfMapper.class);

    DoneBook toDto(Book book);
    BookshelfInfoDto bookToBookshelfInfoDto(Book book);
    DoneBook bookToDoneBook(Book book);
    ReadingBook bookToReadingBook(Book book);
    WishBook bookToWishBook(Book book);
    BookshelfMemoResDto memoToBookshelfMemoResDto(Memo memo);
}
