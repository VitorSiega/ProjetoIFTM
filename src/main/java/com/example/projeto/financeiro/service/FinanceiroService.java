package com.example.projeto.financeiro.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projeto.financeiro.dto.FinanceiroDTO;
import com.example.projeto.financeiro.model.FinanceiroModel;
import com.example.projeto.financeiro.repository.FinanceiroRepository;
import com.example.projeto.login.model.ModelUser;
import com.example.projeto.login.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

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

    public void atualizarCaixinha(List<FinanceiroDTO> financeiroDTO) {
        financeiroDTO.forEach(financeiro -> {
            FinanceiroModel financeiroMod = financeiroRepository.findById(financeiro.id())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            financeiroMod.setDiaPago(financeiro.dia_pago());
            financeiroMod.setValorPago(financeiro.valorPago());
            financeiroMod.setStatus(financeiro.status());
            financeiroRepository.save(financeiroMod);
        });
    }

    public List<FinanceiroModel> buscarCaixinha(LocalDate dataParaControle) {

        List<FinanceiroModel> financeiroModelList = financeiroRepository.findByMesAtualAndAnoAtual(dataParaControle.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), dataParaControle.getYear());
        String mes = dataParaControle.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int ano = dataParaControle.getYear();

        if (financeiroModelList.isEmpty() || financeiroModelList.size() < userRepository.findAll().size()) {

            List<ModelUser> receberUsuarios = userRepository.findByStatusOperador("ATIVO");
            int gerarListaSize = financeiroModelList.size();

            for (int i = gerarListaSize; i < receberUsuarios.size(); i++) {
                ModelUser usuario = receberUsuarios.get(i);
                FinanceiroModel financeiro = FinanceiroModel.builder()
                        .user(usuario)
                        .mesAtual(mes)
                        .anoAtual(ano)
                        .valorPago(15.00)
                        .status("NÃO PAGO")
                        .build();
                financeiroModelList.add(financeiro);
            }
            financeiroRepository.saveAll(financeiroModelList);
            return financeiroModelList;
        } else {
            return financeiroModelList;
        }
    }

}
