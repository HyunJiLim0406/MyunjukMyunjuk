package jpa.myunjuk.module.model.dto.bookshelf;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookshelfDetailDtos {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BookshelfInfoDto{

        private Long id;
        private String description;
        private String publisher;
        private String isbn;
        private Integer totPage;
        private String url;
    }
}
