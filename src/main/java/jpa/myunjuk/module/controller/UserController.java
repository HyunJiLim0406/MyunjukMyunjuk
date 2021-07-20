package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.JwtRefreshReqDto;
import jpa.myunjuk.module.model.dto.user.UserJoinReqDto;
import jpa.myunjuk.module.model.dto.user.UserLogInReqDto;
import jpa.myunjuk.module.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * localhost:8080/join
     * @param userJoinReqDto
     * @return ResponseEntity
     */
    @PostMapping("/join")
    public ResponseEntity<?> join(@Valid @RequestBody UserJoinReqDto userJoinReqDto){
        return new ResponseEntity<>(userService.join(userJoinReqDto), HttpStatus.OK);
    }

    /**
     * 로그인
     * localhost:8080/log-in
     * @param userLogInReqDto
     * @return ResponseEntity
     */
    @PostMapping("/log-in")
    public ResponseEntity<?> logIn(@Valid @RequestBody UserLogInReqDto userLogInReqDto){
        return new ResponseEntity<>(userService.logIn(userLogInReqDto), HttpStatus.OK);
    }

    /**
     * 토큰 재발행
     * localhost:8080/refresh-tokens
     * @param jwtRefreshReqDto
     * @return ResponseEntity
     */
    @PostMapping("/refresh-tokens")
    public ResponseEntity<?> refreshUserToken(@Valid @RequestBody JwtRefreshReqDto jwtRefreshReqDto){
        return new ResponseEntity<>(userService.refreshUserTokens(jwtRefreshReqDto), HttpStatus.OK);
    }

    /**
     * 로그아웃
     * localhost:8080/log-out
     * @param user
     * @return ResponseEntity
     */
    @PostMapping("/log-out")
    public ResponseEntity<?> logOut(@AuthenticationPrincipal User user){
        return new ResponseEntity<>(userService.logOut(user), HttpStatus.OK);
    }
}
