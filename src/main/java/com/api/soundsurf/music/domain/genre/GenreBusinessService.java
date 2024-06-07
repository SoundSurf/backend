package com.api.soundsurf.music.domain.genre;

import com.api.soundsurf.iam.domain.userGenre.UserGenreService;
import com.api.soundsurf.iam.domain.user.UserService;
import com.api.soundsurf.iam.dto.GenreDto;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreBusinessService {
    private final GenreService genreService;
    private final UserService userService;
    private final UserGenreService userGenreService;

    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    public List<UserGenre> selectGenre(final Long userId, final GenreDto.Select.Request requestDto) {
        final User user = userService.findById(userId);

        return selectGenre(user, requestDto.getIds());
    }

    public List<UserGenre> selectGenre(final User user, final List<Long> ids) {
        for (var genreId : ids) {
            Genre selectedGenre = genreService.findById(genreId);
            userGenreService.setUserGenre(user, selectedGenre);
        }

        return userGenreService.getAllByUser(user);
    }

}
