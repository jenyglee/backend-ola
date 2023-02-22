package com.project.sparta.user.repository;

import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    List<UserTag> findAllByUser(User user);

    @Query("select u.tag from UserTag u where u.user.Id=:targetId")
    List<Hashtag> findUserTag(@Param("targetId") Long targetId);
}
