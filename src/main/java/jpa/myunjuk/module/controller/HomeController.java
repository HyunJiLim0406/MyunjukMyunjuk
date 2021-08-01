package jpa.myunjuk.module.controller;

import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.HomeDto;
import jpa.myunjuk.module.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public ResponseEntity<?> home(@AuthenticationPrincipal User user,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) Integer month) {
        log.info("[Request] home " + user.getEmail());
        HomeDto result = homeService.home(user, year, month);
        if(result.getSize()==0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(homeService.home(user, year, month), HttpStatus.OK);
    }
}
