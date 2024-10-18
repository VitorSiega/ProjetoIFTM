package com.example.projeto.presenca.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        // Busca todas as presenças para a data informada
        List<PresencaModel> presencasExistentes = presenceRepository.findByData(dataDoLancamento);
        List<ModelUser> todosUsuarios = userRepository.findAll();

        // Cria uma lista para armazenar novas presenças
        List<PresencaModel> novasPresencas = new ArrayList<>();

        // Mapeia todas as presenças existentes para checar quais usuários já estão
        // cadastrados
        List<Long> idsUsuariosComPresenca = presencasExistentes.stream()
                .map(p -> p.getUser().getId()) // Assume que você tem um método getUser() em PresencaModel
                .collect(Collectors.toList());

        for (ModelUser usuario : todosUsuarios) {
            // Se o usuário não tem presença para a data selecionada, crie um novo registro
            if (!idsUsuariosComPresenca.contains(usuario.getId())) {
                PresencaModel listaGerada = PresencaModel.builder()
                        .user(usuario)
                        .data(dataDoLancamento)
                        .status("falta") // ou o status desejado
                        .build();
                novasPresencas.add(listaGerada); // Adiciona à lista de novas presenças
            }
        }

        // Salva novas presenças no repositório
        if (!novasPresencas.isEmpty()) {
            presenceRepository.saveAll(novasPresencas);
        }

        // Retorna todas as presenças para a data informada, incluindo as novas
        return presenceRepository.findByData(dataDoLancamento);
    }

}
