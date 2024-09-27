package com.example.projeto.presenca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.presenca.dto.PresencaDTO;
import com.example.projeto.presenca.repository.PresencaRepository;
import com.example.projeto.presenca.service.PresencaService;



@RestController
@RequestMapping("/api")
public class PresencaController {
    
    @Autowired
    PresencaRepository presencaRepository;
    @Autowired
    PresencaService presencaService;

    @PostMapping("/admin/presenca")
    public ResponseEntity<?> registrarPresenca(@RequestBody PresencaDTO presencaDTO) {
        presencaService.registrarPresenca(presencaDTO);
        return ResponseEntity.status(201).body(null);
    }
    
}
