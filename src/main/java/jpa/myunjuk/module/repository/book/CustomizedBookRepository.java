package jpa.myunjuk.module.repository.book;

import jpa.myunjuk.module.model.dto.history.ChartPageDto;

import java.util.List;

import static jpa.myunjuk.module.model.dto.history.ChartAmountDto.*;

public interface CustomizedBookRepository {
    List<AmountDto> findByYearCountGroupByMonth(Long userId, int year);
    List<ChartPageDto> findByYearPageGroupByMonth(Long userId, int year);
}
