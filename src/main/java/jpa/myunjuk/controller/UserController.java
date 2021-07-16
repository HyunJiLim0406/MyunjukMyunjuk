package jpa.myunjuk.controller;

import jpa.myunjuk.common.JwtTokenProvider;
import jpa.myunjuk.domain.UserTest;
import jpa.myunjuk.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

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
    public String login(@RequestBody Map<String, String> user){
        UserTest member = userTestRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        if(!passwordEncoder.matches(user.get("password"), member.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호 입니다.");
        }
        return jwtTokenProvider.createToken(member.getUsername(), member.getRoles());
    }
}
