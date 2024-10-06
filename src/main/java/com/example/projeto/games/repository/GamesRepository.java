package com.example.projeto.games.repository;
import com.example.projeto.games.model.GamesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GamesRepository extends JpaRepository<GamesModel, Long> {
}
