package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.S3Exception;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static jpa.myunjuk.module.model.dto.UserDtos.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final S3Service s3Service;

    /**
     * info
     *
     * @param user
     * @return UserInfoDto
     */
    public UserInfoDto info(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    /**
     * updateImg
     *
     * @param user
     * @param file
     */
    @Transactional
    public void updateImg(User user, MultipartFile file) {
        String img = null;
        if (file != null) {
            try {
                img = s3Service.upload(file);
            } catch (IOException e) {
                throw new S3Exception("file = " + file.getOriginalFilename());
            }
        }
        user.updateUserImg(img);
        userRepository.save(user);
    }

    /**
     * updateNickname
     *
     * @param user
     * @param userNickNameReqDto
     */
    @Transactional
    public void updateNickname(User user, UserNickNameReqDto userNickNameReqDto) {
        user.updateNickname(userNickNameReqDto.getNickname());
        userRepository.save(user);
    }

    /**
     * signOut
     *
     * @param user
     * @return UserDto
     */
    @Transactional
    public void signOut(User user) {
        user.setRefreshTokenValue(null);
        userRepository.save(user);
    }
}
