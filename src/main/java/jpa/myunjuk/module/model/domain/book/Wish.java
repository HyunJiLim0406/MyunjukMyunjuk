package jpa.myunjuk.module.model.domain.book;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Getter
@DiscriminatorValue("W")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wish extends Book{

    @NotNull
    private Integer expectScore;

    @Lob
    private String expectation;
}
