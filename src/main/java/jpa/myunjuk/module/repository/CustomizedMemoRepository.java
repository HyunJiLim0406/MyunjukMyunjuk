package jpa.myunjuk.module.repository;

import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;

import java.util.List;

public interface CustomizedMemoRepository {
    List<Memo> findByBookFirst(List<Long> bookIds);
}
