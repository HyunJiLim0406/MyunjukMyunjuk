package jpa.myunjuk.module.repository.memo;

import java.util.List;

public interface CustomizedMemoRepository {
    List<Long> findLatestMemoByBookIds(List<Long> bookIds);
}
