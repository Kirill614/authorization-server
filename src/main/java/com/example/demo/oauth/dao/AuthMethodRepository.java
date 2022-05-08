package com.example.demo.oauth.dao;

import com.example.demo.entity.AuthenticationMethod;
import com.example.demo.entity.EnumAuthMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthMethodRepository extends JpaRepository<AuthenticationMethod, Long> {
    Optional<AuthenticationMethod> findByMethodName(EnumAuthMethod methodName);
}
