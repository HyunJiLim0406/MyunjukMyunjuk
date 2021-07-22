package jpa.myunjuk.module.model.domain;

import com.sun.istack.NotNull;
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
public class Characters {

    @Id
    @Column(name = "character_sn")
    @GeneratedValue
    private Long id;

    @Column(name = "character_name")
    @NotNull
    private String name;

    @Column(name = "character_img")
    @NotNull
    private String img;

    private Integer height;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private String shortDescription;

    @Lob
    @NotNull
    private String longDescription;
}
