package com.example.demo.oauth.dao;

import com.example.demo.entity.AuthGrantType;
import com.example.demo.entity.EnumGrantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthGrantTypeRepository extends JpaRepository<AuthGrantType, Long> {
    Optional<AuthGrantType> findByGrantType(EnumGrantType grantType);
}
