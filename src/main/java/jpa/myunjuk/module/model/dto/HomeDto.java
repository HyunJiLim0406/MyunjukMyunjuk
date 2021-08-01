package jpa.myunjuk.module.model.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeDto {

    private int size;
    private HeightInfo heightInfo;
    private List<Item> itemList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class HeightInfo {
        private String name;
        private String img;
        private double totHeight;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {
        private String title;
        private Integer totPage;
        private int score;
        private String author;
        private String thumbnail;
    }
}
