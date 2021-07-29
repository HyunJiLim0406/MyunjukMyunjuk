package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.AccessDeniedException;
import jpa.myunjuk.infra.exception.InvalidReqBodyException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CommonService {

    private final BookRepository bookRepository;

    public Book getBook(User user, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("Book id = " + id));
        checkUser(user, book);
        return book;
    }

    public void checkUser(User user, Book book) {
        if (!book.getUser().equals(user))
            throw new AccessDeniedException("Book id = " + book.getId());
    }

    public void validateReadPage(int readPage, Integer totPage) {
        if (totPage != null && readPage > totPage)
            throw new InvalidReqBodyException("page = " + readPage + " > " + totPage);
    }

    public void validateDate(LocalDate start, LocalDate end) {
        if (end.isBefore(start))
            throw new InvalidReqBodyException("date = " + start + " < " + end);
    }
}
