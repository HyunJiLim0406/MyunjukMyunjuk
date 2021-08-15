package jpa.myunjuk.module.repository.memo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static jpa.myunjuk.module.model.domain.QMemo.memo;

@RequiredArgsConstructor
public class CustomizedMemoRepositoryImpl implements CustomizedMemoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Long> findLatestMemoByBookIds(List<Long> bookIds) {
        return jpaQueryFactory.select(memo.id.max())
                .from(memo)
                .where(memo.book.id.in(bookIds))
                .groupBy(memo.book)
                .fetch();
    }
}
