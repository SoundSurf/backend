package com.api.soundsurf.iam.domain.userGenre;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGenreService {
    private final UserGenreRepository userGenreRepository;

    public void deleteAllUserGenres(final User user) {
        userGenreRepository.deleteAllByUser(user);
    }

    public void setUserGenre(final User user, final int genre) {
        userGenreRepository.save(UserGenre.newInstance(user, genre));
    }

    public List<UserGenre> getAllByUser(final User user) {
        return userGenreRepository.findAllByUser(user);
    }
}
