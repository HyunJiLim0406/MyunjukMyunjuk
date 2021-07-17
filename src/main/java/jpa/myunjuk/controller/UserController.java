package jpa.myunjuk.controller;

import io.jsonwebtoken.JwtException;
import jpa.myunjuk.common.JwtTokenProvider;
import jpa.myunjuk.domain.UserTest;
import jpa.myunjuk.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTestRepository userTestRepository;

    //회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String, String> user){
        return userTestRepository.save(UserTest.builder()
            .email(user.get("email"))
            .password(passwordEncoder.encode(user.get("password")))
            .roles(Collections.singletonList("ROLE_USER"))
            .build()).getId();
    }

    //로그인
    @PostMapping("/login")
    public String[] login(@RequestBody Map<String, String> user){
        UserTest member = userTestRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if(!passwordEncoder.matches(user.get("password"), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        String[] jwtTokens = createJwtTokens(member, member.getRoles());
        return jwtTokens;
        //return jwtTokenProvider.createAccessToken(member.getUsername(), member.getRoles());
    }

    //token 재발행
    @PostMapping("/refresh-tokens")
    public String[] refreshUserToken(@RequestBody Map<String, String> user){
        String[] refreshTokensResult = refreshUserTokens(user.get("email"), user.get("refreshToken"));
        return refreshTokensResult;
    }

    //토큰 재발행
    public String[] refreshUserTokens(String email, String refreshToken) {
        UserTest member = userTestRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        checkIfRefreshTokenValid(member.getRefreshTokenValue(), refreshToken); //유저의 실제 토큰과 클라이언트에서 넘어온 토큰
        String[] jwtTokens = createJwtTokens(member, member.getRoles());
        return jwtTokens;
    }

    private void checkIfRefreshTokenValid(String requiredValue, String givenRefreshToken) { //유효한지 확인
        String givenValue = String.valueOf(jwtTokenProvider.getClaimsFromJwtToken(givenRefreshToken).getBody().get("value"));
        if(!givenValue.equals(requiredValue)){
            System.out.println("나중에 예외 처리 추가 필요");
        }
    }

    private String[] createJwtTokens(UserTest user, List<String> roles){
        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), roles);
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", "");
        saveRefreshTokenValue(user, refreshTokenValue);
        String refreshToken = jwtTokenProvider.createRefreshToken(refreshTokenValue);
        return new String[]{accessToken, refreshToken};
    }

    private void saveRefreshTokenValue(UserTest user, String refreshToken){ //사용자의 refreshToken 데베에 저장
        user.setRefreshTokenValue(refreshToken);
        userTestRepository.save(user);
    }
}
