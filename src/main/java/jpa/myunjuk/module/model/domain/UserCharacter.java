package jpa.myunjuk.module.model.domain;

import com.sun.istack.NotNull;
import jpa.myunjuk.infra.converter.BooleanToYNConverter;
import lombok.*;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public UserCharacter(Long id, User user, Characters characters, LocalDate achieve, boolean representation) {
        Assert.notNull(user, "User must not be null");
        Assert.notNull(characters, "Characters must not be null");
        Assert.notNull(achieve, "Achieve must not be null");

        this.id = id;
        this.user = user;
        this.characters = characters;
        this.achieve = achieve;
        this.representation = representation;
        if (!user.getUserCharacters().contains(this))
            user.getUserCharacters().add(this);
    }
}
