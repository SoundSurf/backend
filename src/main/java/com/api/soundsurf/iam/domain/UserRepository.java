package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Integer countAllByEmail(final String email);

    Optional<User> findByEmail(final String email);
}
