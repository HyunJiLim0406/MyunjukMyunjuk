package jpa.myunjuk.module.model.dto.history;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChartAmountDto {

    private Long totalCount;
    private List<AmountDto> itemList = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class AmountDto {

        private Integer month;
        private Long count;
    }
}
