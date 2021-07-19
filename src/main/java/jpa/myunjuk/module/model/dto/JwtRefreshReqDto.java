package jpa.myunjuk.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NotNull
public class JwtRefreshReqDto {

    private String email;
    private String refreshToken;
}
