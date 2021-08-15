package jpa.myunjuk.module.model.dto.history;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChartPageDto {

    private Integer month;
    private Integer page;
}
