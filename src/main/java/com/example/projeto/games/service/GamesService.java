package com.example.projeto.games.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.projeto.games.dto.GamesDTO;
import com.example.projeto.games.model.GamesModel;
import com.example.projeto.games.repository.GamesRepository;

@Service
public class GamesService {

    @Autowired
    @Lazy
    private final GamesRepository gamesRepository;

    public GamesService(@Lazy GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public void salvarUsuario(GamesDTO createGamesDTO) {

        // Cria o novo game
        GamesModel newGame = GamesModel.builder()
                .id(createGamesDTO.id())
                .nome(createGamesDTO.nome())
                .dataJogo(createGamesDTO.dataJogo())
                .descricao(createGamesDTO.descricao())
                .local(createGamesDTO.local())
                .situacao(createGamesDTO.situacao())
                .responsaveis(createGamesDTO.responsaveis())
                .build();
        gamesRepository.save(newGame);
    }
}
