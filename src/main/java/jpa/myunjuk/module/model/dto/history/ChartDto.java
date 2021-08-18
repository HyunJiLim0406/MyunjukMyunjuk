package jpa.myunjuk.module.model.dto.history;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChartDto {

    private Long totalCount;
    private List<Item> itemList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Item {

        private Integer month;
        private Long count;
        private Integer page;
    }
}
