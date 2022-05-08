package com.example.demo.user.dao;

import com.example.demo.entity.EnumRole;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(EnumRole role);
}
