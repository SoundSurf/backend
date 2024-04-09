package com.api.soundsurf.iam.domain.userGenre;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import com.api.soundsurf.music.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {
    List<UserGenre> findAllByUser(User user);

    boolean existsByUserAndGenre(User user, Genre genre);

    Integer countAllByUser(final User user);
}
