package com.example.projeto.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto.login.model.ModelUser;

@Repository
public interface UserRepository extends JpaRepository<ModelUser, Long> {

    Optional<ModelUser> findByEmail(String email);

    Optional<ModelUser> findByOperador(int operador);
}
