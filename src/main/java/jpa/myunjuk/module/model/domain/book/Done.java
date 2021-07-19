package jpa.myunjuk.module.model.domain.book;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@DiscriminatorValue("D")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Done extends Book{

    @Column(name = "d_start_date")
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer score;
}
