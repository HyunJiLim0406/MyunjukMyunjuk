package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.history.ChartAmountDto;
import jpa.myunjuk.module.model.dto.history.MemoDto;
import jpa.myunjuk.module.model.dto.history.ChartPageDto;
import jpa.myunjuk.module.repository.book.BookRepository;
import jpa.myunjuk.module.repository.memo.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static jpa.myunjuk.module.model.dto.history.ChartAmountDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final MemoRepository memoRepository;
    private final BookRepository bookRepository;

    /**
     * myMemos
     *
     * @param user
     * @return List<MemoDto>
     */
    public List<MemoDto> myMemos(User user) {
        List<Long> bookIds = user.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList());

        return memoRepository.findByIdIn(memoRepository.findLatestMemoByBookIds(bookIds)).stream()
                .map(o -> MemoDto.builder()
                        .bookId(o.getBook().getId())
                        .title(o.getBook().getTitle())
                        .thumbnail(o.getBook().getThumbnail())
                        .content(o.getContent())
                        .saved(o.getSaved())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * chartAmount
     *
     * @param user
     * @param year
     * @return ChartAmountDto
     */
    public ChartAmountDto chartAmount(User user, int year) {
        validateYear(year);
        List<AmountDto> statistics = bookRepository.findByYearCountGroupByMonth(user.getId(), year);

        return ChartAmountDto.builder()
                .totalCount(statistics.stream().mapToLong(AmountDto::getCount).sum())
                .itemList(statistics)
                .build();
    }

    /**
     * chartPage
     *
     * @param user
     * @param year
     * @return List<ChartPageDto>
     */
    public List<ChartPageDto> chartPage(User user, int year) {
        validateYear(year);
        return bookRepository.findByYearPageGroupByMonth(user.getId(), year);
    }

    private void validateYear(int year) {
        if (year < 2000 || year > LocalDate.now().getYear())
            throw new InvalidReqParamException("year = " + year);
    }
}
