package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.module.mapper.bookshelf.BookshelfMapper;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.bookshelf.BookshelfDetailDtos;
import jpa.myunjuk.module.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookshelfMemoService {

    private final BookshelfMapper bookshelfMapper;
    private final MemoRepository memoRepository;
    private final CommonService commonService;

    /**
     * bookshelfMemo
     *
     * @param user
     * @param id
     * @return List<BookshelfMemoResDto>
     */
    public List<BookshelfDetailDtos.BookshelfMemoResDto> bookshelfMemo(User user, Long id) {
        Book book = commonService.getBook(user, id);
        return memoRepository.findAllByBookOrderBySavedDesc(book).stream()
                .map(bookshelfMapper.INSTANCE::memoToBookshelfMemoResDto)
                .collect(Collectors.toList());
    }

    /**
     * bookshelfAddMemo
     *
     * @param user
     * @param bookshelfMemoReqDto
     * @return Memo
     */
    @Transactional
    public Memo bookshelfAddMemo(User user, BookshelfDetailDtos.BookshelfMemoReqDto bookshelfMemoReqDto) {
        Book book = commonService.getBook(user, bookshelfMemoReqDto.getId());
        return memoRepository.save(Memo.builder()
                .book(book)
                .content(bookshelfMemoReqDto.getContent())
                .saved(LocalDateTime.now())
                .build());
    }

    /**
     * bookshelfUpdateMemo
     *
     * @param user
     * @param id
     * @param bookshelfUpdateMemoReqDto
     */
    @Transactional
    public void bookshelfUpdateMemo(User user, Long id, BookshelfDetailDtos.BookshelfUpdateMemoReqDto bookshelfUpdateMemoReqDto) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("Memo id = " + id));
        commonService.checkUser(user, memo.getBook());

        memo.updateMemo(bookshelfUpdateMemoReqDto.getContent());
        memoRepository.save(memo);
    }

    /**
     * bookshelfDeleteMemo
     *
     * @param user
     * @param id
     */
    @Transactional
    public void bookshelfDeleteMemo(User user, Long id) {
        Memo memo = memoRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("Memo id = " + id));
        commonService.checkUser(user, memo.getBook());

        memoRepository.delete(memo);
    }
}
