package jpa.myunjuk.module.model.dto.bookshelf;

import jpa.myunjuk.module.model.domain.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BookshelfBasic {

    private Long id;
    private String title;
    private String author;
    private String thumbnail;
    private BookStatus bookStatus;
}
