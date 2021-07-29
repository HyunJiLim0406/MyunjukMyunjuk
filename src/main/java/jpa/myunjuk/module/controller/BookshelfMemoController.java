package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos;
import jpa.myunjuk.module.service.BookshelfMemoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("bookshelf/memo")
@RequiredArgsConstructor
public class BookshelfMemoController {

    private final BookshelfMemoService bookshelfMemoService;

    /**
     * 책의 메모
     * localhost:8080/bookshelf/memo?bookId=42
     *
     * @param user
     * @param bookId
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity<?> bookshelfRetrieveMemo(@AuthenticationPrincipal User user,
                                                   @RequestParam Long bookId) {
        log.info("[Request] Retrieve all Memos in book id = " + bookId);
        List<BookshelfDetailDtos.BookshelfMemoResDto> result = bookshelfMemoService.bookshelfMemo(user, bookId);
        if (result.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 메모 추가하기
     * localhost:8080/bookshelf/memo
     *
     * @param user
     * @param bookshelfMemoReqDto
     * @return ResponseEntity
     */
    @PostMapping("")
    public ResponseEntity<?> bookshelfMemo(@AuthenticationPrincipal User user,
                                           @Valid @RequestBody BookshelfDetailDtos.BookshelfMemoReqDto bookshelfMemoReqDto) {
        log.info("[Request] Add memo book id = " + bookshelfMemoReqDto.getId());
        bookshelfMemoService.bookshelfAddMemo(user, bookshelfMemoReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 메모 수정하기
     * localhost:8080/bookshelf/memo/233
     *
     * @param user
     * @param memoId
     * @param bookshelfUpdateMemoReqDto
     * @return ResponseEntity
     */
    @PutMapping("/{memoId}")
    public ResponseEntity<?> bookshelfMemo(@AuthenticationPrincipal User user,
                                           @PathVariable Long memoId,
                                           @Valid @RequestBody BookshelfDetailDtos.BookshelfUpdateMemoReqDto bookshelfUpdateMemoReqDto) {
        log.info("[Request] Update memo Memo id = " + memoId);
        bookshelfMemoService.bookshelfUpdateMemo(user, memoId, bookshelfUpdateMemoReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 메모 삭제하기
     * localhost:8080/bookshelf/memo/233
     *
     * @param user
     * @param memoId
     * @return ResponseEntity
     */
    @DeleteMapping("/{memoId}")
    public ResponseEntity<?> bookshelfMemo(@AuthenticationPrincipal User user,
                                           @PathVariable Long memoId) {
        log.info("[Request] Delete memo Memo id = " + memoId);
        bookshelfMemoService.bookshelfDeleteMemo(user, memoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
