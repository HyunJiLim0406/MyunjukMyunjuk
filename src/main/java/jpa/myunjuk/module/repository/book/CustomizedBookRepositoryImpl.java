package jpa.myunjuk.module.repository.book;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.myunjuk.module.model.domain.BookStatus;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static jpa.myunjuk.module.model.domain.QBook.*;
import static jpa.myunjuk.module.model.dto.history.ChartDto.*;

@RequiredArgsConstructor
public class CustomizedBookRepositoryImpl implements CustomizedBookRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Item> findByYearGroupByMonth(Long userId, int year) {
        return jpaQueryFactory.select(
                Projections.constructor(
                        Item.class,
                        book.endDate.month(),
                        book.count(),
                        book.totPage.sum()))
                .from(book)
                .where(book.user.id.eq(userId)
                        .and(book.bookStatus.eq(BookStatus.DONE))
                        .and(book.endDate.year().eq(year)))
                .groupBy(book.endDate.month())
                .fetch();
    }
}
