package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.history.ChartDto;
import jpa.myunjuk.module.model.dto.history.MemoDto;
import jpa.myunjuk.module.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    /**
     * 각 책마다 제일 최근에 저장한 메모 불러오기
     * localhost:8080/history/my-memos
     *
     * @param user
     * @return ResponseEntity
     */
    @GetMapping("/my-memos")
    public ResponseEntity<?> myMemos(@AuthenticationPrincipal User user) {
        log.info("[Request] Retrieve all Memos " + user.getEmail());
        List<MemoDto> result = historyService.myMemos(user);
        if (result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 읽은 책 차트 보기
     * localhost:8080/history/chart
     *
     * @param user
     * @param year
     * @return ResponseEntity
     */
    @GetMapping("/chart")
    public ResponseEntity<?> chart(@AuthenticationPrincipal User user,
                                   @RequestParam int year) {
        log.info("[Request] Chart " + user.getEmail());
        ChartDto result = historyService.chart(user, year);
        if (result.getTotalCount() == 0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
