package jpa.myunjuk.module.model.domain;

import com.sun.istack.NotNull;
import jpa.myunjuk.infra.converter.BooleanToYNConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCharacter {

    @Id
    @Column(name = "uc_sn")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_sn")
    @NotNull
    private Characters characters;

    @NotNull
    private LocalDate achieve;

    @Convert(converter = BooleanToYNConverter.class)
    @NotNull
    private boolean representation;
}
