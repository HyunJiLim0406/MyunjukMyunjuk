package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.service.CharactersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("character")
@RequiredArgsConstructor
public class CharactersController {

    private final CharactersService charactersService;

    /**
     * 캐릭터 리스트
     * localhost:8080/character
     *
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<?> characterList() {
        log.info("[Request] Character list");
        return new ResponseEntity<>(charactersService.characterList(), HttpStatus.OK);
    }

    /**
     * 상세 캐릭터
     * localhost:8080/character/detail?characterId=1
     *
     * @param characterId
     * @return ResponseEntity
     */
    @GetMapping("/detail")
    public ResponseEntity<?> characterDetail(@RequestParam Long characterId) {
        log.info("[Request] Character detail info " + characterId);
        return new ResponseEntity<>(charactersService.characterDetail(characterId), HttpStatus.OK);
    }

    /**
     * 사용자가 보유한 캐릭터 조회
     * localhost:8080/character/user
     *
     * @param user
     * @return ResponseEntity
     */
    @GetMapping("/user")
    public ResponseEntity<?> userCharacterList(@AuthenticationPrincipal User user) {
        log.info("[Request] User character list " + user.getEmail());
        return new ResponseEntity<>(charactersService.userCharacterList(user), HttpStatus.OK);
    }

    /**
     * 사용자의 대표 캐릭터 변경
     * localhost:8080/character/user/44
     *
     * @param user
     * @param userCharacterId
     * @return ResponseEntity
     */
    @PutMapping("/user/{userCharacterId}")
    public ResponseEntity<?> updateCharacterRepresentation(@AuthenticationPrincipal User user,
                                                           @PathVariable Long userCharacterId) {
        log.info("[Request] Update Representation " + userCharacterId);
        charactersService.updateCharacterRepresentation(user, userCharacterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
