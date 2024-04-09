package com.api.soundsurf.music.domain;

import com.api.soundsurf.iam.domain.usergenre.UserGenreService;
import com.api.soundsurf.iam.domain.user.UserService;
import com.api.soundsurf.iam.dto.GenreDto;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.iam.exception.UserGenreAlreadyExistsException;
import com.api.soundsurf.iam.exception.UserGenreCountException;
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

        for (var genreId : requestDto.getIds()) {
            Genre selectedGenre = genreService.findById(genreId);
            userGenreService.setUserGenre(user, selectedGenre);
        }

        return userGenreService.getAllByUser(user);
    }

}
