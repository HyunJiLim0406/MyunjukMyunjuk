package jpa.myunjuk.module.repository.book;

import java.util.List;

import static jpa.myunjuk.module.model.dto.history.ChartDto.*;

public interface CustomizedBookRepository {
    List<Item> findByYearGroupByMonth(Long userId, int year);
}
