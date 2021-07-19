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
@DiscriminatorValue("R")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reading extends Book{

    @Column(name = "r_start_date")
    @NotNull
    private LocalDate start_date;

    @NotNull
    private Integer page;
}
