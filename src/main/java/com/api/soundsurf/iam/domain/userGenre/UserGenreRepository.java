package com.api.soundsurf.iam.domain.userGenre;

import com.api.soundsurf.iam.entity.User;
import com.api.soundsurf.iam.entity.UserGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {
    void deleteAllByUser(User user);
    List<UserGenre> findAllByUser(User user);
}
