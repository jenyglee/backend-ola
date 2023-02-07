package com.project.sparta.hashtag.service;

import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.User;

public interface HashtagService {
    Hashtag createHashtag(String value, User user);
}
