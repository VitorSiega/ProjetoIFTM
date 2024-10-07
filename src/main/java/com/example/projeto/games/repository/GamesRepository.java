package com.example.projeto.games.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto.games.model.GamesModel;

@Repository
public interface GamesRepository extends JpaRepository<GamesModel, Long> {
}
