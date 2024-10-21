package com.example.projeto.presenca.service;

import java.time.LocalDate;
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
        if (presenceRepository.findByData(dataDoLancamento).isEmpty()
                || presenceRepository.findByData(dataDoLancamento).size() < userRepository.findAll().size()) {
            List<PresencaModel> gerarLista = presenceRepository.findByData(dataDoLancamento);
            List<ModelUser> receberUsuarios = userRepository.findAll();
            int gerarListaSize = gerarLista.size();

            for (int i = gerarListaSize; i < receberUsuarios.size(); i++) {
                ModelUser usuario = receberUsuarios.get(i);
                PresencaModel listaGerada = PresencaModel.builder()
                        .user(usuario)
                        .data(dataDoLancamento)
                        .status("falta")
                        .build();
                gerarLista.add(listaGerada);
            }
            presenceRepository.saveAll(gerarLista);
            return gerarLista;
        } else {
            return presenceRepository.findByData(dataDoLancamento);
        }
    }

}
