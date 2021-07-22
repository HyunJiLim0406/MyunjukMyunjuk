package jpa.myunjuk.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

public class JwtDtos {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class JwtRefreshReqDto {

        private String email;
        private String refreshToken;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class JwtDto {

        private Long userId;
        private String accessToken;
        private String refreshToken;
    }
}
