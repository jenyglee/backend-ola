package com.project.sparta.user.repository;


import com.project.sparta.user.entity.StatusEnum;
import com.project.sparta.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long kakaoId);
    Optional<User> findByNickName(String nickName);

    @Query("select u from USERS u where u.Id=:friendId and u.status=:statusEnum")
    Optional<User> findFriend(@Param("friendId") Long friendId, @Param("statusEnum") StatusEnum statusEnum);
}
