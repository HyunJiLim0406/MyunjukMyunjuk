package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("history")
@RequiredArgsConstructor
public class HistoryController {

    @GetMapping("/my-memos")
    public ResponseEntity<?> myMemos(@AuthenticationPrincipal User user) {
        log.info("[Request] Retrieve all Memos " + user.getEmail());
        return null;
    }
}
