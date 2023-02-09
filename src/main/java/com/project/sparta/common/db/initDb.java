package com.project.sparta.common.db;

import com.project.sparta.hashtag.entity.Hashtag;
import com.project.sparta.user.entity.User;
import com.project.sparta.user.entity.UserGradeEnum;
import com.project.sparta.user.entity.UserRoleEnum;
import com.project.sparta.user.entity.UserTag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.project.sparta.admin.entity.StatusEnum.USER_REGISTERED;

@Profile("local")
@Component
@RequiredArgsConstructor
public class initDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        // 사용처: 회원가입 시 선택할 해시태그 목록
        initService.hashtagInit();
    }

    @Component
    static class InitService{
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void hashtagInit(){
            em.persist(new Hashtag("주변맛집"));
            em.persist(new Hashtag("등산용품"));
            em.persist(new Hashtag("등산데이트"));
            em.persist(new Hashtag("등산친구"));
            em.persist(new Hashtag("봄산"));
            em.persist(new Hashtag("여름산"));
            em.persist(new Hashtag("가을산"));
            em.persist(new Hashtag("겨울산"));
            em.persist(new Hashtag("안전수칙"));
            em.persist(new Hashtag("산악회"));
            em.persist(new Hashtag("10~20대"));
            em.persist(new Hashtag("30~40대"));
            em.persist(new Hashtag("50~60대"));
            em.persist(new Hashtag("등산꿀팁"));
            em.persist(new Hashtag("산추천"));
            em.persist(new Hashtag("계곡추천"));
            em.persist(new Hashtag("등린이"));
            em.persist(new Hashtag("등산입문자"));
            em.persist(new Hashtag("등산매니아"));
            em.persist(new Hashtag("등산모임"));
            em.persist(new Hashtag("1시간걸리는산"));
            em.persist(new Hashtag("2시간걸리는산"));
            em.persist(new Hashtag("3시간걸리는산"));
        }
    }
}

