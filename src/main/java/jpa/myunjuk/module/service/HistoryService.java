package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.history.ChartDto;
import jpa.myunjuk.module.model.dto.history.MemoDto;
import jpa.myunjuk.module.repository.book.BookRepository;
import jpa.myunjuk.module.repository.memo.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static jpa.myunjuk.module.model.dto.history.ChartDto.*;

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
     * chart
     *
     * @param user
     * @param year
     * @return ChartDto
     */
    public ChartDto chart(User user, int year) {
        validateYear(year);
        List<Item> statistics = bookRepository.findByYearGroupByMonth(user.getId(), year);

        return ChartDto.builder()
                .totalCount(statistics.stream().mapToLong(Item::getCount).sum())
                .itemList(statistics)
                .build();
    }

    private void validateYear(int year) {
        if (year < 2000 || year > LocalDate.now().getYear())
            throw new InvalidReqParamException("year = " + year);
    }
}
