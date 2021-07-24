package jpa.myunjuk.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchDto implements Serializable {

    List<Document> documents = new ArrayList<>();
    Meta meta = new Meta();

    static class Document{
        public List<String> authors = new ArrayList<>();
        public String contents;
        public String isbn;
        public String publisher;
        public String thumbnail;
        public String title;
    }

    static class Meta{
        public Integer total_count;
    }
}
