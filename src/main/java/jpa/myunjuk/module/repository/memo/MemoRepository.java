package jpa.myunjuk.module.repository.memo;

import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long>, CustomizedMemoRepository {
    List<Memo> findAllByBookOrderBySavedDesc(Book book);
    List<Memo> findByIdIn(List<Long> id);
}
