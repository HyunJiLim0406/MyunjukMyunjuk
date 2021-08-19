package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static jpa.myunjuk.module.model.dto.UserDtos.*;

@Slf4j
@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 정보
     * localhost:8080/profile
     *
     * @param user
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<?> profile(@AuthenticationPrincipal User user) {
        log.info("[Request] User profile " + user.getEmail());
        return new ResponseEntity<>(profileService.profile(user), HttpStatus.OK);
    }

    /**
     * 사용자 정보
     * localhost:8080/profile/info
     *
     * @param user
     * @return ResponseEntity
     */
    @GetMapping("/info")
    public ResponseEntity<?> info(@AuthenticationPrincipal User user) {
        log.info("[Request] User info " + user.getEmail());
        return new ResponseEntity<>(profileService.info(user), HttpStatus.OK);
    }

    /**
     * 사용자 이미지 수정
     * localhost:8080/profile/info/img
     *
     * @param user
     * @param img
     * @return ResponseEntity
     */
    @PutMapping("/info/img")
    public ResponseEntity<?> img(@AuthenticationPrincipal User user,
                                 @RequestPart(required = false) MultipartFile img) {
        log.info("[Request] Update user img " + user.getEmail());
        profileService.updateImg(user, img);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 사용자 닉네임 수정
     * localhost:8080/profile/info/nickname
     *
     * @param user
     * @param userNickNameReqDto
     * @return ResponseEntity
     */
    @PutMapping("/info/nickname")
    public ResponseEntity<?> nickname(@AuthenticationPrincipal User user,
                                      @RequestBody UserNickNameReqDto userNickNameReqDto) {
        log.info("[Request] Update user nickname " + user.getNickname());
        profileService.updateNickname(user, userNickNameReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 로그아웃
     * localhost:8080/profile/sign-out
     *
     * @param user
     * @return ResponseEntity
     */
    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(@AuthenticationPrincipal User user) {
        log.info("[Request] sign-out " + user.getEmail());
        profileService.signOut(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
