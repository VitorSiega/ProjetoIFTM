package com.example.projeto.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projeto.login.model.ModelUser;

public interface UserRepository extends JpaRepository<ModelUser, Long> {
    Optional<ModelUser> findByEmail(String email);
    Optional<ModelUser> findByOperador(int operador);
}
