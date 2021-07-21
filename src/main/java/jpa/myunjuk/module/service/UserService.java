package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.DuplicateUserException;
import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.infra.jwt.JwtTokenProvider;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.JwtDto;
import jpa.myunjuk.module.model.dto.JwtRefreshReqDto;
import jpa.myunjuk.module.model.dto.user.UserSignUpReqDto;
import jpa.myunjuk.module.model.dto.user.UserSignInReqDto;
import jpa.myunjuk.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * signUp
     * @param userSignUpReqDto
     * @return void
     */
    public void signUp(UserSignUpReqDto userSignUpReqDto) {
        checkDuplicateUser(userSignUpReqDto.getEmail());
        userRepository.save(buildUserFromUserJoinDto(userSignUpReqDto));
    }

    private void checkDuplicateUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(param -> {
                    throw new DuplicateUserException("email = " + email);
                });
    }

    /**
     * signIn
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
     * @param jwtRefreshReqDto
     * @return JwtDto
     */
    public JwtDto refreshUserTokens(JwtRefreshReqDto jwtRefreshReqDto){
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
     * @param user
     * @return UserDto
     */
    public void signOut(User user){
        user.setRefreshTokenValue(null);
        userRepository.save(user);
    }

    private User buildUserFromUserJoinDto(UserSignUpReqDto userSignUpReqDto) { //기본 캐릭터 추가해야 함
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
