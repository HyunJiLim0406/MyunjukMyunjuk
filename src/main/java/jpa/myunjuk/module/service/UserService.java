package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.DuplicateUserException;
import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.infra.jwt.JwtTokenProvider;
import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.domain.UserCharacter;
import jpa.myunjuk.module.repository.CharactersRepository;
import jpa.myunjuk.module.repository.UserCharacterRepository;
import jpa.myunjuk.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static jpa.myunjuk.module.model.dto.JwtDtos.*;
import static jpa.myunjuk.module.model.dto.UserDtos.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserCharacterRepository userCharacterRepository;
    private final CharactersRepository charactersRepository;

    /**
     * signUp
     *
     * @param userSignUpReqDto
     * @return User
     */
    public User signUp(UserSignUpReqDto userSignUpReqDto) {
        checkDuplicateUser(userSignUpReqDto.getEmail()); //중복 가입 체크
        Characters characters = charactersRepository.findById(1L).orElseThrow(() -> new NoSuchDataException("Missing default character"));
        User user = userRepository.save(buildUserFromUserJoinDto(userSignUpReqDto));
        userCharacterRepository.save(UserCharacter.builder() //회원가입시 주어지는 기본캐릭터
                .user(user)
                .characters(characters)
                .achieve(LocalDate.now())
                .representation(true)
                .build());
        return user;
    }

    private void checkDuplicateUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(param -> {
                    throw new DuplicateUserException("email = " + email);
                });
    }

    /**
     * signIn
     *
     * @param userSignInReqDto
     * @return JwtDto
     */
    public JwtDto signIn(UserSignInReqDto userSignInReqDto) {
        User user = userRepository.findByEmail(userSignInReqDto.getEmail())
                .orElseThrow(() -> new NoSuchDataException("email = " + userSignInReqDto.getEmail()));
        if (!passwordEncoder.matches(userSignInReqDto.getPassword(), user.getPassword()))
            throw new NoSuchDataException("password = " + userSignInReqDto.getPassword());
        String[] jwtTokens = createJwtTokens(user, user.getRoles());
        return buildJwtDto(user, jwtTokens);
    }

    private String[] createJwtTokens(User user, List<String> roles) {
        String accessToken = jwtTokenProvider.createAccessToken(user.getUsername(), roles);
        String refreshTokenValue = UUID.randomUUID().toString().replace("-", "");
        saveRefreshTokenValue(user, refreshTokenValue);
        String refreshToken = jwtTokenProvider.createRefreshToken(refreshTokenValue);
        return new String[]{accessToken, refreshToken};
    }

    private void saveRefreshTokenValue(User user, String refreshToken) { //사용자의 refreshToken 데베에 저장
        user.setRefreshTokenValue(refreshToken);
        userRepository.save(user);
    }

    /**
     * refreshToken
     *
     * @param jwtRefreshReqDto
     * @return JwtDto
     */
    public JwtDto refreshUserTokens(JwtRefreshReqDto jwtRefreshReqDto) {
        User user = userRepository.findByEmail(jwtRefreshReqDto.getEmail())
                .orElseThrow(() -> new NoSuchDataException("email = " + jwtRefreshReqDto.getEmail()));
        checkIfRefreshTokenValid(user.getRefreshTokenValue(), jwtRefreshReqDto.getRefreshToken()); //유저의 실제 토큰과 클라이언트에서 넘어온 토큰
        String[] jwtTokens = createJwtTokens(user, user.getRoles());
        return buildJwtDto(user, jwtTokens);
    }

    private void checkIfRefreshTokenValid(String requiredValue, String givenRefreshToken) { //유효한지 확인
        String givenValue = String.valueOf(jwtTokenProvider.getClaimsFromJwtToken(givenRefreshToken).get("value"));
        if (!givenValue.equals(requiredValue)) {
            throw new InvalidReqParamException("Invalid refreshToken");
        }
    }

    /**
     * signOut
     *
     * @param user
     * @return UserDto
     */
    public void signOut(User user) {
        user.setRefreshTokenValue(null);
        userRepository.save(user);
    }

    private User buildUserFromUserJoinDto(UserSignUpReqDto userSignUpReqDto) {
        return User.builder()
                .email(userSignUpReqDto.getEmail())
                .password(passwordEncoder.encode(userSignUpReqDto.getPassword()))
                .nickname(userSignUpReqDto.getNickname())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    private JwtDto buildJwtDto(User user, String[] jwtTokens) {
        return JwtDto.builder()
                .userId(user.getId())
                .accessToken(jwtTokens[0])
                .refreshToken(jwtTokens[1])
                .build();
    }
}