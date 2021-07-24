package jpa.myunjuk.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResDto {

    private Integer totalResults;
    private Item[] items;

    static class Item {

        private String title;
        private String author;
        private String isbn;
        private String description;
        private String cover;
    }
}
