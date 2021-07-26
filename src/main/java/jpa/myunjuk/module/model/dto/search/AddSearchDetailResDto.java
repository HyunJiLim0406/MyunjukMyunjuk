package jpa.myunjuk.module.model.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddSearchDetailResDto {

    private Long id;
    private String name;
    private String img;
}
