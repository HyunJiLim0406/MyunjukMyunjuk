package jpa.myunjuk.module.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLogInReqDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
