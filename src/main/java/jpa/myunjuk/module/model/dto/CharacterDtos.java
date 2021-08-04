package jpa.myunjuk.module.model.dto;

import lombok.*;

import java.time.LocalDate;

public class CharacterDtos {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CharacterListDto {

        private Long id;
        private String name;
        private String img;
        private String shortDescription;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CharacterDto {

        private Long id;
        private String name;
        private String img;
        private double height;
        private LocalDate birthday;
        private String shortDescription;
        private String longDescription;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserCharacterDto {

        private Long userCharacterId;
        private Long characterId;
        private String name;
        private String img;
        private double height;
        private LocalDate achieve;
        private boolean representation;
    }
}
