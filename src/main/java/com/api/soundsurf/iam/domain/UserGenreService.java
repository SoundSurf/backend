package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Genre;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGenreService {
    private final UserGenreRepository userGenreRepository;

    public UserGenre setUserGenre(final User user, Genre genre) {
        return userGenreRepository.save(UserGenre.newInstance(user, genre));
    }

    public List<UserGenre> getAllByUser(User user) {
        return userGenreRepository.findAllByUser(user);
    }

    public boolean existsByUserAndGenre(User user, Genre genre) {
        return userGenreRepository.existsByUserAndGenre(user, genre);
    }
}
