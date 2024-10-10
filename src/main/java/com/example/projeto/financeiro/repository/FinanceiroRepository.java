package com.example.projeto.financeiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projeto.financeiro.model.FinanceiroModel;

@Repository
public interface FinanceiroRepository extends JpaRepository<FinanceiroModel, Long> {

    List<FinanceiroModel> findByMesAtualAndAnoAtual(String mes, int ano);
}
