package com.example.projeto.games.service;

import com.example.projeto.games.dto.GamesDTO;
import com.example.projeto.games.model.GamesModel;
import com.example.projeto.games.repository.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
@Service
public class GamesService {

    @Autowired
    @Lazy
    private final GamesRepository gamesRepository;

    @Autowired
    @Lazy
    private final GamesService gamesService;

    public GamesService(@Lazy GamesRepository gamesRepository, @Lazy GamesService gamesService) {
        this.gamesRepository = gamesRepository;
        this.gamesService = gamesService;
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
