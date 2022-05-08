package com.example.demo.client;

import com.example.demo.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("db")
public interface JpaClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByClientId(String clientId);
}
