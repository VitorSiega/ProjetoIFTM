package com.example.projeto.seguranca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.seguranca.enums.Role;
import com.example.projeto.seguranca.model.ModelRole;

public interface RoleRepository extends JpaRepository<ModelRole, Long> {
    Optional<ModelRole> findByName(Role name);
}
