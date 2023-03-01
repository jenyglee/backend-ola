package com.project.sparta.like.repository;

import java.util.List;

public interface LikeCommentCustomRepository {

  Boolean isLike();

  Long updateCountLike();

  void bulkUpdateLike();

  Long initCountLike();

}
