package com.project.sparta.hashtag.repository;

import com.project.sparta.hashtag.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {
    Optional<Hashtag> findByName(String name);

    @Query("select h from Hashtag h where h.name like %:name%")
    Page<Hashtag> findAllByLikeName(@Param("name") String name, Pageable pageable);
}
