package jpa.myunjuk.module.model.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Memo {

    @Id
    @Column(name = "memo_sn")
    @GeneratedValue
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

    public void updateMemo(String content){
        this.content = content;
    }
}