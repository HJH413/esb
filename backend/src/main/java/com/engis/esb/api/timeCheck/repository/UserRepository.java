package com.engis.esb.api.timeCheck.repository;

import com.engis.esb.api.timeCheck.domain.EsbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<EsbUser, Long> {
    Optional<EsbUser> findByUserIdAndUserPw(String userId, String userPw);
}
