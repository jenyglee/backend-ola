package com.project.sparta.hashtag.repository;

import com.project.sparta.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag,Long> {

}
