package com.project.sparta.user.repository;


import com.project.sparta.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomerRepository {

    Optional<User> findByUserName(String username);
}
