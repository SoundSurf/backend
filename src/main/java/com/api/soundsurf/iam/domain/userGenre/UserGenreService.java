package com.api.soundsurf.iam.domain.userGenre;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGenreService {
    private final UserGenreRepository userGenreRepository;

    public UserGenre setUserGenre(final User user,final Genre genre) {
        return userGenreRepository.save(UserGenre.newInstance(user, genre));
    }

    public List<UserGenre> getAllByUser(final User user) {
        return userGenreRepository.findAllByUser(user);
    }

    public boolean existsByUserAndGenre(final User user,final Genre genre) {
        return userGenreRepository.existsByUserAndGenre(user, genre);
    }

    public Integer countByUser(final User user) {
        return userGenreRepository.countAllByUser(user);
    }
}
