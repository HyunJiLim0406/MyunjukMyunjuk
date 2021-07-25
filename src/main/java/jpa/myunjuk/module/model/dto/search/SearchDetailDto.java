package jpa.myunjuk.module.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDetailDto {

    List<Items> items = new ArrayList<>();

    public static class Items{

        public String title;
        public String link;
        public String image;
        public String author;
        public String publisher;
        public String isbn;
        public String description;
    }
}
