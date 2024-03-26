package com.api.soundsurf.iam.domain;

import com.api.soundsurf.iam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Integer countAllByNickname(final String nickName);
}
