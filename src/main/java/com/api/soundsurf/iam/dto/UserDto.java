package com.api.soundsurf.iam.dto;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.stream.Collectors;

public class UserDto {
    public static class Create {
        @Getter
        @Schema(name = "UserDto.Create.Request")
        public static class Request {
            @Email
            private String email;
            @Length(min = 6, max = 16)
            private String password;
        }

        @AllArgsConstructor
        @Schema(name = "UserDto.Create.Response")
        @Getter
        public static class Response {
            private String userToken;
        }
    }

    public static class Login {
        @Getter
        @Schema(name = "UserDto.Login.Request")
        public static class Request {
            @Email
            private String email;
            @NotNull
            private String password;
        }

        @AllArgsConstructor
        @Schema(name = "UserDto.Login.Response")
        @Getter
        public static class Response {
            private String userToken;
            private Info.Response userInfo;
        }
    }

    public static class Info {
        @Schema(name = "UserDto.Info.Response")
        @Getter
        public static class Response {
            final Long userId;
            final String userEmail;
            final String nickname;
            final List<Integer> genres;
            final Boolean newUser;
            final Long carId;
            final String imageBucketPath;
            final Long savedMusicCount;
            final Long playListCount;

            public Response(final User user, final Long carId, final String imageBucketPath) {
                this.userId = user.getId();
                this.userEmail = user.getEmail();
                this.nickname = user.getNickname();
                this.newUser = user.isNewUser();
                this.carId = carId;
                this.genres = user.getUserGenres().stream().map(UserGenre::getGenreId).collect(Collectors.toList());
                this.imageBucketPath = imageBucketPath;
                this.savedMusicCount = (long) user.getSavedMusics().size();
                this.playListCount = (long) user.getPlaylists().size();
            }
        }
    }

    public static class SetNickname {
        @Getter
        @Schema(name = "UserDto.SetNickname.Request")
        public static class Request {
            @NotNull
            @Length(max=20)
            private String nickname;
        }

        @AllArgsConstructor
        @Getter
        @Schema(name = "UserDto.SetNickname.Response")
        public static class Response {
            private String nickname;
        }
    }

}
