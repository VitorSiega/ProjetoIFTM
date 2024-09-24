package com.example.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.enums.Role;
import com.example.projeto.model.ModelRole;

public interface RoleRepository extends JpaRepository<ModelRole, Long> {
    Optional<ModelRole> findByName(Role name);
}
