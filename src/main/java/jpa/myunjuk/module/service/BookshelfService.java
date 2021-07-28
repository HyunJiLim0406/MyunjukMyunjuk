package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.AccessDeniedException;
import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.mapper.bookshelf.BookshelfMapper;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.BookStatus;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookshelfService {

    private final BookRepository bookRepository;
    private final BookshelfMapper bookshelfMapper;

    /**
     * bookShelf
     *
     * @param user
     * @param bookStatus
     * @return List<Object>
     */
    public List<Object> bookshelf(User user, String bookStatus) {
        validateInput(bookStatus);

        List<Object> bookList = new ArrayList<>();
        user.getBooks().stream()
                .sorted(Comparator.comparing(Book::getId).reversed()) //가장 최근에 저장된 책부터 나오도록 정렬
                .filter(o -> bookStatus == null || o.getBookStatus() == BookStatus.from(bookStatus)) //검색 조건 필터링
                .forEach(o -> {
                    if (o.getBookStatus() == BookStatus.DONE)
                        bookList.add(bookshelfMapper.toDoneBookDto(o));
                    if (o.getBookStatus() == BookStatus.READING)
                        bookList.add(bookshelfMapper.toReadingBookDto(o));
                    if (o.getBookStatus() == BookStatus.WISH)
                        bookList.add(bookshelfMapper.toWishBookDto(o));
                });
        return bookList;
    }

    private void validateInput(String bookStatus) { //enum 확인
        if (Arrays.stream(BookStatus.values())
                .noneMatch(o -> bookStatus == null || o.toString().equalsIgnoreCase(bookStatus)))
            throw new InvalidReqParamException("BookStatus = " + bookStatus);
    }

    /**
     * bookshelfDetailInfo
     *
     * @param user
     * @param id
     * @return BookshelfInfoDto
     */
    public BookshelfInfoDto bookshelfDetailInfo(User user, Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("Book id = " + id));
        checkUser(user, book);
        return BookshelfInfoDto.builder()
                .id(book.getId())
                .description(book.getDescription())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .totPage(book.getTotPage())
                .url(book.getUrl())
                .build();
    }

    private void checkUser(User user, Book book) {
        if (!book.getUser().equals(user))
            throw new AccessDeniedException("Book id = " + book.getId());
    }
}
