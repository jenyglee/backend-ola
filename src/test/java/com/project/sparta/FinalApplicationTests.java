package com.project.sparta;

import com.project.sparta.entity.Hello;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class FinalApplicationTests {
    @Autowired
    EntityManager em;
    @Test
    void contextLoads() {
        Hello hello = new Hello();
        em.persist(hello);

        Hello hello1 = em.find(Hello.class, hello.getId());
        Assertions.assertThat(hello).isEqualTo(hello1);
    }

}
