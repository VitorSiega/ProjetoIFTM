package com.example.projeto.presenca.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projeto.login.model.ModelUser;
import com.example.projeto.login.repository.UserRepository;
import com.example.projeto.presenca.dto.PresencaDTO;
import com.example.projeto.presenca.model.PresencaModel;
import com.example.projeto.presenca.repository.PresencaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PresencaService {

    @Autowired
    private final PresencaRepository presenceRepository;

    @Autowired
    private final UserRepository userRepository;

    public PresencaService(PresencaRepository presencaRepository, UserRepository userRepository) {
        this.presenceRepository = presencaRepository;
        this.userRepository = userRepository;
    }

    public void registrarPresenca(List<PresencaDTO> listaAtualizar) {
        listaAtualizar.forEach(list -> {
            PresencaModel presenca = presenceRepository.findById(list.id())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
            presenca.setStatus(list.status());
            presenceRepository.save(presenca);
        });
    }

    public List<PresencaModel> buscarPresenca(LocalDate dataDoLancamento) {
        // Obter todas as presenças para a data especificada
        List<PresencaModel> presencasExistentes = presenceRepository.findByData(dataDoLancamento);

        // Obter todos os usuários
        List<ModelUser> receberUsuarios = userRepository.findAll();

        // Se não houver presenças registradas ou o número de presenças for menor que o
        // número de usuários
        if (presencasExistentes.isEmpty() || presencasExistentes.size() < receberUsuarios.size()) {
            // Lista para armazenar as presenças geradas
            List<PresencaModel> gerarLista = new ArrayList<>(presencasExistentes);

            // Adiciona presenças para usuários que ainda não têm uma presença registrada
            for (ModelUser usuario : receberUsuarios) {
                // Verifica se o usuário já está presente na lista existente
                boolean usuarioPresente = presencasExistentes.stream()
                        .anyMatch(p -> p.getUser().getId().equals(usuario.getId()));

                if (!usuarioPresente) {
                    PresencaModel listaGerada = PresencaModel.builder()
                            .user(usuario)
                            .data(dataDoLancamento)
                            .status("falta") // Considere usar um Enum aqui
                            .build();
                    gerarLista.add(listaGerada);
                }
            }

            // Salva todas as presenças (novas e existentes)
            presenceRepository.saveAll(gerarLista);
            return gerarLista;
        } else {
            return presencasExistentes;
        }
    }
}
