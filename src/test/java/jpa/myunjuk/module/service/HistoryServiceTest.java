package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.Memo;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("History Service Test")
@Transactional
class HistoryServiceTest {

    @Autowired
    HistoryService historyService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Retrieve my memos | Success")
    void memosSuccess() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        List<Memo> memos = historyService.myMemos(user);
        for (Memo memo : memos)
            System.out.println("memo.toString() = " + memo.toString());
        System.out.println("memos.size() = " + memos.size());
    }
}