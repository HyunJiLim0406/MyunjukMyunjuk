package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.infra.exception.S3Exception;
import jpa.myunjuk.module.mapper.BookshelfMapper;
import jpa.myunjuk.module.mapper.CharactersMapper;
import jpa.myunjuk.module.model.domain.*;
import jpa.myunjuk.module.model.dto.search.AddSearchDetailResDto;
import jpa.myunjuk.module.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final CharactersService charactersService;
    private final CommonService commonService;
    private final CharactersMapper charactersMapper;
    private final S3Service s3Service;

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
                        bookList.add(bookshelfMapper.INSTANCE.toDoneBook(o));
                    if (o.getBookStatus() == BookStatus.READING)
                        bookList.add(bookshelfMapper.INSTANCE.toReadingBook(o));
                    if (o.getBookStatus() == BookStatus.WISH)
                        bookList.add(bookshelfMapper.INSTANCE.toWishBook(o));
                });
        return bookList;
    }

    private void validateInput(String bookStatus) { //enum 확인
        if (Arrays.stream(BookStatus.values())
                .noneMatch(o -> bookStatus == null || o.toString().equalsIgnoreCase(bookStatus)))
            throw new InvalidReqParamException("BookStatus = " + bookStatus);
    }

    /**
     * bookshelfUpdate
     *
     * @param user
     * @param id
     * @param req
     * @return AddSearchDetailResDto
     */
    @Transactional
    public AddSearchDetailResDto bookshelfUpdate(User user, Long id, BookshelfUpdateReqDto req) {
        Book book = commonService.getBook(user, id);
        commonService.validateReadPage(req.getReadPage(), book.getTotPage());
        commonService.validateDate(req.getStartDate(), req.getEndDate());
        BookStatus before = book.getBookStatus(), after = BookStatus.from(req.getBookStatus());

        book.updateBookStatus(BookStatus.from(req.getBookStatus()), req.getStartDate(), req.getEndDate(),
                req.getScore(), req.getReadPage(), req.getExpectation());
        bookRepository.save(book);

        //책 상태의 변화에 따라 캐릭터를 추가 or 삭제
        if (before == BookStatus.DONE && after != BookStatus.DONE)
            charactersService.removeCharacters(user);
        if (before != BookStatus.DONE && after == BookStatus.DONE)
            return charactersMapper.INSTANCE.toAddSearchDetailResDto(charactersService.addNewCharacters(user));
        return null;
    }

    /**
     * bookshelfDelete
     *
     * @param user
     * @param id
     */
    @Transactional
    public void bookshelfDelete(User user, Long id) {
        Book book = commonService.getBook(user, id);

        BookStatus bookStatus = book.getBookStatus();
        user.getBooks().remove(book);
        bookRepository.delete(book);
        if (bookStatus == BookStatus.DONE) //삭제된 책이 읽은 책이라면
            charactersService.removeCharacters(user);
    }

    /**
     * bookshelfInfo
     *
     * @param user
     * @param id
     * @return BookshelfInfoDto
     */
    public BookshelfInfoDto bookshelfInfo(User user, Long id) {
        Book book = commonService.getBook(user, id);
        return bookshelfMapper.INSTANCE.toBookshelfInfoDto(book);
    }

    /**
     * bookshelfUpdateInfo
     *
     * @param user
     * @param id
     * @param file
     * @param req
     */
    @Transactional
    public void bookshelfUpdateInfo(User user, Long id, MultipartFile file, BookshelfInfoUpdateReqDto req) {
        Book book = commonService.getBook(user, id);
        String thumbnail = book.getThumbnail();
        if (file != null) {
            try {
                thumbnail = s3Service.upload(file);
            } catch (IOException e) {
                throw new S3Exception("file = " + file.getOriginalFilename());
            }
        }
        book.updateBookInfo(req.getTitle(), req.getAuthor(), req.getPublisher(), req.getTotPage(), thumbnail);
        bookRepository.save(book);
    }
}
