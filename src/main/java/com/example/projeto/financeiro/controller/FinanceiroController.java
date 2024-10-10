package com.example.projeto.financeiro.controller;

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

import com.example.projeto.financeiro.dto.FinanceiroDTO;
import com.example.projeto.financeiro.model.FinanceiroModel;
import com.example.projeto.financeiro.service.FinanceiroService;

@RestController
@RequestMapping("/api/admin/financeiro")
public class FinanceiroController {

    @Autowired
    private FinanceiroService financeiroService;

    // @Autowired
    // private FinanceiroRepository financeiroRepository;
    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarCaixinha(@RequestBody List<FinanceiroDTO> financeiroDTO) {

        //List<FinanceiroDTO> listaReduzida = new ArrayList<>();
        // financeiroDTO.forEach(financeiro -> {
        //     if (financeiro.status().equals("PAGO")) {
        //         listaReduzida.add(financeiro);
        //     }
        // });
        // financeiroService.atualizarCaixinha(listaReduzida);
        financeiroService.atualizarCaixinha(financeiroDTO);
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FinanceiroModel>> listarCaixinha(@RequestParam("buscarPresencaData") LocalDate dataBuscar) {
        List<FinanceiroModel> caixinha = financeiroService.buscarCaixinha(dataBuscar);
        return ResponseEntity.ok(caixinha);
    }

}
