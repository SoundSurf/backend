package com.api.soundsurf.iam.domain.user;

import com.api.soundsurf.api.utils.S3ImageUploader;
import com.api.soundsurf.iam.domain.userGenre.UserGenreService;
import com.api.soundsurf.iam.dto.UserDto;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.exception.NicknameDuplicateException;
import com.api.soundsurf.iam.exception.PasswordConditionException;
import com.api.soundsurf.iam.exception.PasswordNotMatchException;
import com.api.soundsurf.music.domain.genre.GenreBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserBusinessService {
    private final UserService service;
    private final BCryptPasswordEncoder passwordEncoder;
    private final GenreBusinessService genreBusinessService;
    private final UserGenreService userGenreService;
    private final S3ImageUploader s3ImageUploader;

    private final String DEFAULT_IMAGE_S3_BUCKET_PATH = "https://soundsurf.s3.ap-northeast-2.amazonaws.com/default_profile_image.png";

    public Long create(final String email, final String password, final String qrS3BucketPath) {
        validateCreate(email, password);

        final var encryptPassword = encryptPassword(password);

        final var newUser = new User(email, encryptPassword, qrS3BucketPath, DEFAULT_IMAGE_S3_BUCKET_PATH, 1L);

        return service.create(newUser);
    }

    public User login(final UserDto.Login.Request requestDto) {
        final var user = findByEmail(requestDto.getEmail());
        validatePassword(user, requestDto.getPassword());

        user.setNewUser(false);
        service.update(user);

        return user;
    }

    public void update(final Long userId, final Long updateCarId, final List<Long> updateGenreIds, final String updateNickname, final MultipartFile updateImage) {
        final var user = service.findById(userId);

        if (updateCarId != null) {
            user.setCarId(updateCarId);
        }

        // TODO: 장르 추가
        if (updateGenreIds != null && !updateGenreIds.isEmpty()) {
            userGenreService.deleteAllUserGenres(user);
            genreBusinessService.selectGenre(user, updateGenreIds);
        }

        if (updateNickname != null) {
            user.setNickname(updateNickname);
        }

        if(updateImage != null) {
            final var s3BucketPath = s3ImageUploader.uploadImage(updateImage, "profile");
            user.setImageS3BucketPath(s3BucketPath.getS3ImagePath());
        }

        service.update(user);
    }

    public String getQr(final Long userId) {
        final var user = service.findById(userId);
        return user.getQrS3BucketPath();
    }


    public User getUser(final Long id) {
        return service.findById(id);
    }


    private User findByEmail(final String email) {
        return service.findByEmail(email);
    }

    private void validatePassword(final User user, final String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordNotMatchException();
        }
    }

    private void validateCreate(final String email, final String password) {
        validateNoDuplicateEmail(email);
        validatePasswordHaveEngAndDigit(password);
    }

    private void validateNoDuplicateEmail(final String email) {
        if (service.countByEmail(email) > 0) {
            throw new NicknameDuplicateException(email);
        }
    }

    private void validatePasswordHaveEngAndDigit(final String password) {
        final var letterPattern = Pattern.compile("[a-zA-Z]");
        final var digitPattern = Pattern.compile("[0-9]");

        final var hasLetter = letterPattern.matcher(password);
        final var hasDigit = digitPattern.matcher(password);

        if (!hasLetter.find() || !hasDigit.find()) {
            throw new PasswordConditionException();
        }
    }

    private String encryptPassword(final String password) {
        return passwordEncoder.encode(password);
    }

    public String setNickname(final Long userId, final String nickname) {
        final User user = service.findById(userId);
        validateNoDuplicateNickname(nickname);

        user.setNickname(nickname);
        service.update(user);

        return nickname;
    }

    private void validateNoDuplicateNickname(final String nickname) {
        if (service.existsByNickname(nickname)) {
            throw new NicknameDuplicateException(nickname);
        }
    }
}
