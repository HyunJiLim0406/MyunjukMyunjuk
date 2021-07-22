package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.JwtRefreshReqDto;
import jpa.myunjuk.module.model.dto.user.UserSignUpReqDto;
import jpa.myunjuk.module.model.dto.user.UserSignInReqDto;
import jpa.myunjuk.module.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * localhost:8080/sign-up
     *
     * @param userSignUpReqDto
     * @return ResponseEntity
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> join(@Valid @RequestBody UserSignUpReqDto userSignUpReqDto) {
        log.info("[Request] sign-up");
        userService.signUp(userSignUpReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 로그인
     * localhost:8080/sign-in
     *
     * @param userSignInReqDto
     * @return ResponseEntity
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody UserSignInReqDto userSignInReqDto) {
        log.info("[Request] sign-in " + userSignInReqDto.toString());
        return new ResponseEntity<>(userService.signIn(userSignInReqDto), HttpStatus.OK);
    }

    /**
     * 토큰 재발행
     * localhost:8080/refresh-tokens
     *
     * @param jwtRefreshReqDto
     * @return ResponseEntity
     */
    @PostMapping("/refresh-tokens")
    public ResponseEntity<?> refreshUserToken(@Valid @RequestBody JwtRefreshReqDto jwtRefreshReqDto) {
        log.info("[Request] refresh-tokens");
        return new ResponseEntity<>(userService.refreshUserTokens(jwtRefreshReqDto), HttpStatus.OK);
    }

    /**
     * 로그아웃
     * localhost:8080/sign-out
     *
     * @param user
     * @return ResponseEntity
     */
    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal User user) {
        log.info("[Request] sign-out");
        userService.signOut(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
