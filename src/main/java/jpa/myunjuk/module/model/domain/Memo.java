package jpa.myunjuk.module.model.domain;

import com.sun.istack.NotNull;
import jpa.myunjuk.module.model.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Memo {

    @Id
    @Column(name = "memo_sn")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_sn")
    @NotNull
    private Book book;

    @Lob
    @NotNull
    private String content;

    @NotNull
    private LocalDateTime saved;
}
