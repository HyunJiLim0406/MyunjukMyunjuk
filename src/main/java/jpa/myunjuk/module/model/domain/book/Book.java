package jpa.myunjuk.module.model.domain.book;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor
@AllArgsConstructor
public abstract class Book {

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

    private Integer price;
}
