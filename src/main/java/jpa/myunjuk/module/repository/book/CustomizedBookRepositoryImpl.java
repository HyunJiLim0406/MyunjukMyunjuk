package jpa.myunjuk.module.repository.book;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.myunjuk.module.model.dto.history.ChartPageDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static jpa.myunjuk.module.model.domain.QBook.*;
import static jpa.myunjuk.module.model.dto.history.ChartAmountDto.*;

@RequiredArgsConstructor
public class CustomizedBookRepositoryImpl implements CustomizedBookRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AmountDto> findByYearCountGroupByMonth(Long userId, int year) {
        return jpaQueryFactory.select(
                Projections.constructor(
                        AmountDto.class,
                        book.endDate.month(),
                        book.count()))
                .from(book)
                .where(book.user.id.eq(userId)
                        .and(book.endDate.year().eq(year)))
                .groupBy(book.endDate.month())
                .fetch();
    }

    @Override
    public List<ChartPageDto> findByYearPageGroupByMonth(Long userId, int year) {
        return jpaQueryFactory.select(
                Projections.constructor(
                        ChartPageDto.class,
                        book.endDate.month(),
                        book.totPage.sum()))
                .from(book)
                .where(book.user.id.eq(userId)
                        .and(book.endDate.year().eq(year)))
                .groupBy(book.endDate.month())
                .fetch();
    }
}
