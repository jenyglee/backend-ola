package com.project.sparta.like.repository;

import com.project.sparta.like.entity.BoardLike;
import com.project.sparta.like.entity.CountLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountLikeRepository extends JpaRepository<CountLike, Long> {

}
