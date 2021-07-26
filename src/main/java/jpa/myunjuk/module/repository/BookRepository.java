package jpa.myunjuk.module.repository;

import jpa.myunjuk.module.model.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
