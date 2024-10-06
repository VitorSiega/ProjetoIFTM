package com.example.projeto.games.controller;
import com.example.projeto.games.dto.GamesDTO;
import com.example.projeto.games.repository.GamesRepository;
import com.example.projeto.games.service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class GamesController {

    @Autowired
    GamesService gamesService;

    @PostMapping("/jogos")
    public ResponseEntity<String> cadastrarJogo(@RequestBody GamesDTO gamesDTO) {
        try {
            if (gamesDTO.nome().isEmpty() || gamesDTO.descricao().isEmpty() ||
                    gamesDTO.local().isEmpty() ||  gamesDTO.responsaveis().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preencha todos os dados");
            }

            gamesService.salvarUsuario(gamesDTO);
            return ResponseEntity.status(201).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar jogo: " + e.getMessage());
        }
    }


}
