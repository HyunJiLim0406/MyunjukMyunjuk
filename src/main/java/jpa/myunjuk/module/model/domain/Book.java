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

    @NotNull
    private String title;

    private String thumbnail;

    private String author;

    private String publisher;

    @Lob
    private String description;

    @NotNull
    private Integer isbn;

    private Integer totPage;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer score;

    private Integer readPage;

    @Lob
    private String expectation;
}
