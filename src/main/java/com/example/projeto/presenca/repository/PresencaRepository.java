package com.example.projeto.presenca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto.presenca.model.PresencaModel;

@Repository
public interface PresencaRepository extends JpaRepository<PresencaModel, Long> {
    
}
