package com.example.demo.user.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByMail(String mail);
    Optional<User> findByUsername(String username);
}
