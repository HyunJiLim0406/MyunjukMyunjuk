package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import jpa.myunjuk.module.model.dto.search.SearchReqDto;
import jpa.myunjuk.module.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 책 검색
     * localhost:8080/search
     *
     * @param keyword
     * @param start
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<?> search(
            @RequestParam String keyword,
            @RequestParam int start) {
        log.info("[Request] search");
        return new ResponseEntity<>(searchService.search(keyword, start), HttpStatus.OK);
    }

    /**
     * 책 상세 정보
     * localhost:8080/search/detail
     *
     * @param isbn
     * @return ResponseEntity
     */
    @GetMapping("/detail")
    public ResponseEntity<?> searchDetail(@RequestParam String isbn) {
        log.info("[Request] search detail");
        return new ResponseEntity<>(searchService.searchDetail(isbn), HttpStatus.OK);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> searchDetail(@AuthenticationPrincipal User user, @Valid @RequestBody SearchReqDto searchReqDto) {
        log.info("[Request] Add book " + user.getEmail());
        AddSearchDetailResDto result = searchService.addSearchDetail(user, searchReqDto);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
