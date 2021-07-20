package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.DuplicateUserException;
import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.infra.exception.NoSuchDataException;
import jpa.myunjuk.infra.jwt.JwtTokenProvider;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.JwtDto;
import jpa.myunjuk.module.model.dto.JwtRefreshReqDto;
import jpa.myunjuk.module.model.dto.user.UserDto;
import jpa.myunjuk.module.model.dto.user.UserJoinReqDto;
import jpa.myunjuk.module.model.dto.user.UserLogInReqDto;
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
     * join
     * @param userJoinReqDto
     * @return UserDto
     */
    public UserDto join(UserJoinReqDto userJoinReqDto) {
        checkDuplicateUser(userJoinReqDto.getEmail());
        User newUser = userRepository.save(buildUserFromUserJoinDto(userJoinReqDto));
        return buildUserDtoFromUser(newUser);
    }

    private void checkDuplicateUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(param -> {
                    throw new DuplicateUserException("email = " + email);
                });
    }

    /**
     * logIn
     * @param userLogInReqDto
     * @return JwtDto
     */
    public JwtDto logIn(UserLogInReqDto userLogInReqDto) {
        User user = userRepository.findByEmail(userLogInReqDto.getEmail())
                .orElseThrow(() -> new NoSuchDataException("email = " + userLogInReqDto.getEmail()));
        if (!passwordEncoder.matches(userLogInReqDto.getPassword(), user.getPassword()))
            throw new NoSuchDataException("password = " + userLogInReqDto.getPassword());
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
     * logOut
     * @param user
     * @return UserDto
     */
    public UserDto logOut(User user){
        user.setRefreshTokenValue(null);
        userRepository.save(user);
        return buildUserDtoFromUser(user);
    }

    private User buildUserFromUserJoinDto(UserJoinReqDto userJoinReqDto) { //기본 캐릭터 추가해야 함
        return User.builder()
                .email(userJoinReqDto.getEmail())
                .password(passwordEncoder.encode(userJoinReqDto.getPassword()))
                .nickname(userJoinReqDto.getNickname())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    private UserDto buildUserDtoFromUser(User user){
        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImg(user.getImg())
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
