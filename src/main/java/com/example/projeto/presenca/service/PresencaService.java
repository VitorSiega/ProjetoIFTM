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

@Service
public class PresencaService {

    @Autowired
    private PresencaRepository presencaRepository;
    @Autowired
    private UserRepository userRepository;

    public PresencaService(PresencaRepository presencaRepository, UserRepository userRepository) {
        this.presencaRepository = presencaRepository;
        this.userRepository = userRepository;
    }

    public void registrarPresenca(List<PresencaDTO> presencaDTO){
        List<PresencaModel> listaPresenca = new ArrayList<>();

        presencaDTO.forEach(presenca -> {
            ModelUser user = userRepository.findById(presenca.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            PresencaModel presencaModelNew = PresencaModel.builder()
            .user(user)
            .data(presenca.data())
            .status(presenca.status())
            .build();
            listaPresenca.add(presencaModelNew);
        });
        presencaRepository.saveAll(listaPresenca);
    }
    
    public List<PresencaModel> buscarPresenca(LocalDate dataBuscar) {
        return presencaRepository.findByData(dataBuscar);
    }
}
