package jpa.myunjuk.module.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static jpa.myunjuk.module.model.domain.QMemo.memo;

@RequiredArgsConstructor
public class CustomizedMemoRepositoryImpl implements CustomizedMemoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Memo> findByBookFirst(List<Long> bookIds) {
        return jpaQueryFactory.selectFrom(memo)
                .where(memo.book.id.in(bookIds))
                .orderBy(memo.saved.desc())
                .groupBy(memo.book)
                .fetch();
    }
}
