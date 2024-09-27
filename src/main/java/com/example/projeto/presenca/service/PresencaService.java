package com.example.projeto.presenca.service;

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

    public PresencaService(PresencaRepository presencaRepository, UserRepository userRepository){
        this.presencaRepository = presencaRepository;
        this.userRepository = userRepository;
    }

    public void registrarPresenca(PresencaDTO presencaDTO){
        ModelUser user = userRepository.findById(presencaDTO.userId())
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        PresencaModel newPresenca = PresencaModel.builder()
        .user(user)
        .data(presencaDTO.data())
        .status(presencaDTO.status())
        .build();
        
        presencaRepository.save(newPresenca);
    }
}
