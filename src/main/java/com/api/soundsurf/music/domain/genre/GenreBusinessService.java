package com.api.soundsurf.music.domain.genre;

import com.api.soundsurf.iam.domain.userGenre.UserGenreService;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.list.domain.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreBusinessService {
    private final UserGenreService userGenreService;

    public void selectGenre(final User user, final List<Long> ids) {
        ids.forEach(genreId -> userGenreService.setUserGenre(user,genreId.intValue()));

        userGenreService.getAllByUser(user);
    }
}
