package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import jpa.myunjuk.module.service.BookshelfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.util.List;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.*;

@Slf4j
@Validated
@RestController
@RequestMapping("bookshelf")
@RequiredArgsConstructor
public class BookshelfController {

    private final BookshelfService bookshelfService;

    /**
     * 내 서재 조회
     * localhost:8080/bookshelf?bookStatus=done
     *
     * @param user
     * @param bookStatus
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<?> bookshelf(@AuthenticationPrincipal User user,
                                       @RequestParam(required = false) String bookStatus) {
        log.info("[Request] Retrieve all books " + user.getEmail());
        List<Object> result = bookshelfService.bookshelf(user, bookStatus);
        if (result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 책 상태 수정
     * localhost:8080/42
     *
     * @param user
     * @param bookId
     * @param bookshelfUpdateReqDto
     * @return ResponseEntity
     */
    @PutMapping("/{bookId}")
    public ResponseEntity<?> bookshelf(@AuthenticationPrincipal User user,
                                       @PathVariable Long bookId,
                                       @Valid @RequestBody BookshelfUpdateReqDto bookshelfUpdateReqDto) {
        log.info("[Request] Update book " + bookId);
        AddSearchDetailResDto result = bookshelfService.bookshelfUpdate(user, bookId, bookshelfUpdateReqDto);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 책 삭제
     * localhost:8080/42
     *
     * @param user
     * @param bookId
     * @return ResponseEntity
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> bookshelf(@AuthenticationPrincipal User user,
                                       @PathVariable Long bookId) {
        log.info("[Request] Delete book " + bookId);
        bookshelfService.bookshelfDelete(user, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 책의 나머지 정보들
     * localhost:8080/bookshelf/info?bookId=42
     *
     * @param user
     * @param bookId
     * @return ResponseEntity
     */
    @GetMapping("/info")
    public ResponseEntity<?> bookshelfInfo(@AuthenticationPrincipal User user,
                                           @RequestParam Long bookId) {
        log.info("[Request] Retrieve one book info " + bookId);
        return new ResponseEntity<>(bookshelfService.bookshelfInfo(user, bookId), HttpStatus.OK);
    }

    /**
     * 책 정보 수정
     * localhost:8080/bookshelf/info/42
     *
     * @param user
     * @param bookId
     * @param thumbnail
     * @param bookshelfInfoUpdateReqDto
     * @return ResponseEntity
     */
    @PutMapping(value = "/info/{bookId}")
    public ResponseEntity<?> bookshelfInfo(@AuthenticationPrincipal User user,
                                           @PathVariable Long bookId,
                                           @RequestPart(required = false) MultipartFile thumbnail,
                                           @Valid @RequestPart BookshelfInfoUpdateReqDto bookshelfInfoUpdateReqDto) {
        log.info("[Request] Update book info " + bookId);
        bookshelfService.bookshelfUpdateInfo(user, bookId, thumbnail, bookshelfInfoUpdateReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
