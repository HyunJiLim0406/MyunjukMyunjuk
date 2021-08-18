package jpa.myunjuk.module.model.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @Column(name = "user_sn")
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(length = 300)
    @NotNull
    private String password;

    @NotNull
    private String nickname;

    @Column(name = "user_img")
    private String img;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserCharacter> userCharacters = new ArrayList<>();

    @Setter
    private String refreshTokenValue;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public double bookHeight() {
        return books.stream()
                .filter(o -> o.getTotPage() != null)
                .filter(o -> o.getBookStatus() == BookStatus.DONE)
                .mapToInt(Book::getTotPage).sum() * 0.005;
    }

    public void updateUserImg(String img) {
        this.img = img;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}