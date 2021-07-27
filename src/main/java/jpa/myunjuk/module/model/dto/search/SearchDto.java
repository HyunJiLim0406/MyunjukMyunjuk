package jpa.myunjuk.module.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto {

    public Integer total;
    List<Items> items = new ArrayList<>();

    static class Items{
        public String title;
        public String image;
        public String author;
        public String isbn;
        public String description;
    }
}
