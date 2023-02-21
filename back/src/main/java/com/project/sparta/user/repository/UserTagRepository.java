package com.project.sparta.user.repository;

import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    List<UserTag> findAllByUser(User user);
}
