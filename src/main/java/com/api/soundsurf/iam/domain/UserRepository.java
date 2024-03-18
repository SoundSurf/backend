package com.api.soundsurf.iam.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Integer countAllByUsername(final String userName);
    Integer countAllByUsernameAndDeletedIsFalse(final String userName);
}
