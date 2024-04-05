package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.dto.GenreDto;
import com.api.soundsurf.iam.entity.Genre;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.iam.exception.UserGenreAlreadyExistsException;
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

    public List<UserGenre> selectGenre(final List<GenreDto.Select.Request> requestDtos) {
        final User user = userService.findById(requestDtos.get(0).getUserId());
        for (GenreDto.Select.Request requestDto : requestDtos) {
            Genre selectedGenre = genreService.findByName(requestDto.getGenreName());
            validateNoDuplicateUserGenre(user, selectedGenre);
            userGenreService.setUserGenre(user, selectedGenre);
        }
        return userGenreService.getAllByUser(user);
    }

    private void validateNoDuplicateUserGenre(final User user, final Genre genre) {
        if (userGenreService.existsByUserAndGenre(user, genre)) {
            throw new UserGenreAlreadyExistsException(user, genre);
        }
    }
}
