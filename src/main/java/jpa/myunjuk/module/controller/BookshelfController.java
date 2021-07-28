package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.service.BookshelfService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("bookshelf")
@RequiredArgsConstructor
public class BookshelfController {

    private final BookshelfService bookshelfService;

    @GetMapping("")
    public ResponseEntity<?> bookshelf(@AuthenticationPrincipal User user,
                                       @RequestParam(required = false) String bookStatus) {
        log.info("[Request] Retrieve all books " + user.getEmail());
        return new ResponseEntity<>(bookshelfService.bookshelf(user, bookStatus), HttpStatus.OK);
    }

    @GetMapping("/detail/info")
    public ResponseEntity<?> bookshelfDetailInfo(@AuthenticationPrincipal User user,
                                                 @RequestParam Long bookId) {
        log.info("[Request] Retrieve one book info " + user.getEmail());
        return new ResponseEntity<>(bookshelfService.bookshelfDetailInfo(user, bookId), HttpStatus.OK);
    }
}
