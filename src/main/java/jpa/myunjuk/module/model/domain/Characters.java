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

    private double height;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private String shortDescription;

    @Lob
    @NotNull
    private String longDescription;
}