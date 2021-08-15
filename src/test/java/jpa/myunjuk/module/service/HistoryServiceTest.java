package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.history.ChartAmountDto;
import jpa.myunjuk.module.model.dto.history.MemoDto;
import jpa.myunjuk.module.model.dto.history.ChartPageDto;
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

        List<MemoDto> memoDtos = historyService.myMemos(user);
        assertEquals(2, memoDtos.size());
    }

    @Test
    @DisplayName("Chart for Read Book amount | Success")
    void chartAmount() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        ChartAmountDto chartAmountDto = historyService.chartAmount(user, 2019);
        assertEquals(7, chartAmountDto.getTotalCount());
    }

    @Test
    @DisplayName("Chart for Read Book page | Success")
    void chartPage() {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        List<ChartPageDto> chartPageDtos = historyService.chartPage(user, 2019);
        assertEquals(4, chartPageDtos.size());
    }

    @Test
    @DisplayName("Chart | Fail : Invalid year")
    void chartFail() throws Exception {
        User user = userRepository.findByEmail("test@test.com").orElse(null);
        assertNotNull(user);

        InvalidReqParamException e = assertThrows(InvalidReqParamException.class, () ->
                historyService.chartAmount(user, 13));
        assertEquals("year = 13", e.getMessage());
    }
}