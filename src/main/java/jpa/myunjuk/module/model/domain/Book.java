package jpa.myunjuk.module.model.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @Column(name = "book_sn")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn")
    @NotNull
    private User user;

    @NotNull
    private String title;

    @Column(length = 300)
    private String thumbnail;

    private String author;

    private String publisher;

    @Lob
    private String description;

    @NotNull
    private Integer isbn;

    private Integer totPage;

    @Column(length = 300)
    @NotNull
    private String url;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer score;

    private Integer readPage;

    @Lob
    private String expectation;
}