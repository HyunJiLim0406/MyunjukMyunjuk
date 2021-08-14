package jpa.myunjuk.module.service;

import jpa.myunjuk.module.model.domain.Book;
import jpa.myunjuk.module.model.domain.Memo;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private final MemoRepository memoRepository;

    public List<Memo> myMemos(User user){
        List<Long> collect = user.getBooks().stream()
                .map(Book::getId)
                .collect(Collectors.toList());
        return memoRepository.findByBookFirst(collect);
    }
}
