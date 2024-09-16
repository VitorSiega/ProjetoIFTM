package com.example.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.model.ModelUser;

public interface UserRepository extends JpaRepository<ModelUser, Long> {
    Optional<ModelUser> findByEmail(String email);
}
