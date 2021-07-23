package jpa.myunjuk.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class AladinApiDtos {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class AladinSearchDto {

        private String title;
        private String author;
        private String description;
        private String img;
    }
}
