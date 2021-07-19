package jpa.myunjuk.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {

    private Long userId;
    private String accessToken;
    private String refreshToken;
}
