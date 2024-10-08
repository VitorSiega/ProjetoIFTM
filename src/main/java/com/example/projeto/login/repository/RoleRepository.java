package com.example.projeto.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto.login.enums.Role;
import com.example.projeto.login.model.ModelRole;

@Repository
public interface RoleRepository extends JpaRepository<ModelRole, Long> {

    Optional<ModelRole> findByName(Role name);
}
