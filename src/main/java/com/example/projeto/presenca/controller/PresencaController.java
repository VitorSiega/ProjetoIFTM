package com.example.projeto.presenca.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.presenca.dto.PresencaDTO;
import com.example.projeto.presenca.model.PresencaModel;
import com.example.projeto.presenca.service.PresencaService;

@RestController
@RequestMapping("/api/admin/presenca")
public class PresencaController {

    @Autowired
    PresencaService presencaService;

    @PutMapping("/lancar")
    public ResponseEntity<?> registrarPresenca(@RequestBody List<PresencaDTO> presencaDTO) {

        // List<PresencaDTO> listaReduzida = new ArrayList<>();
        // presencaDTO.forEach(list -> {
        //     if (list.status().equals("presente")) {
        //         listaReduzida.add(list);
        //     }
        // });
        // presencaService.registrarPresenca(listaReduzida);
        presencaService.registrarPresenca(presencaDTO);
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PresencaModel>> listarPresencaPorData(@RequestParam("buscarPresencaData") LocalDate dataBuscar) {
        List<PresencaModel> presencas = presencaService.buscarPresenca(dataBuscar);
        return ResponseEntity.ok(presencas);
    }

}
