package com.example.projeto.presenca.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.projeto.presenca.model.PresencaModel;

import jakarta.transaction.Transactional;

@Repository
public interface PresencaRepository extends JpaRepository<PresencaModel, Long> {

    List<PresencaModel> findByData(LocalDate data);

    @Modifying
    @Transactional
    @Query("DELETE FROM PresencaModel p WHERE p.data = :data")
    void deleteByData(LocalDate data);

    @Query("SELECT DISTINCT p.data FROM PresencaModel p ORDER BY p.data ASC")
    List<LocalDate> findDistinctData();

}
