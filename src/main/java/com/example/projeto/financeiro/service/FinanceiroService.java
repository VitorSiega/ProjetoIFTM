package com.example.projeto.financeiro.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projeto.financeiro.model.FinanceiroModel;
import com.example.projeto.financeiro.repository.FinanceiroRepository;
import com.example.projeto.login.model.ModelUser;
import com.example.projeto.login.repository.UserRepository;

@Service
public class FinanceiroService {

    @Autowired
    private final FinanceiroRepository financeiroRepository;
    @Autowired
    private final UserRepository userRepository;

    public FinanceiroService(FinanceiroRepository financeiroRepository, UserRepository userRepository) {
        this.financeiroRepository = financeiroRepository;
        this.userRepository = userRepository;
    }

    public List<FinanceiroModel> buscarCaixinha(LocalDate mesAtual) {
        LocalDate diaAtual = LocalDate.now();
        if (financeiroRepository.findByMesAtual(mesAtual.getDayOfMonth()).isEmpty()) {
            List<FinanceiroModel> gerarLista = new ArrayList<>();
            List<ModelUser> receberUsuarios = userRepository.findAll();

            receberUsuarios.forEach(usuario -> {
                FinanceiroModel.builder()
                        .build();
            });

            financeiroRepository.saveAll(gerarLista);
            return gerarLista;

        } else {
            return financeiroRepository.findByMesAtual(mesAtual.getMonthValue());
        }
    }

}
