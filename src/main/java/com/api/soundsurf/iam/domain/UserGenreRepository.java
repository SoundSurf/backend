package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.Genre;
import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {
    UserGenre save(UserGenre userGenre);

    List<UserGenre> findAllByUser(User user);

    boolean existsByUserAndGenre(User user, Genre genre);
}
