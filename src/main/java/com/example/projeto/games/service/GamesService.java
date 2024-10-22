// package com.example.projeto.games.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Lazy;
// import org.springframework.stereotype.Service;

// import com.example.projeto.games.dto.GamesDTO;
// import com.example.projeto.games.model.GamesModel;
// import com.example.projeto.games.repository.GamesRepository;

// import jakarta.persistence.EntityNotFoundException;

// @Service
// public class GamesService {

// @Autowired
// @Lazy
// private final GamesRepository gamesRepository;

// public GamesService(@Lazy GamesRepository gamesRepository) {
// this.gamesRepository = gamesRepository;
// }

// public void salvarJogo(GamesDTO createGamesDTO) {

// // Cria o novo game
// GamesModel newGame = GamesModel.builder()
// .id(createGamesDTO.id())
// .nome(createGamesDTO.nome())
// .dataJogo(createGamesDTO.dataJogo())
// .descricao(createGamesDTO.descricao())
// .local(createGamesDTO.local())
// .situacao(createGamesDTO.situacao())
// .responsaveis(createGamesDTO.responsaveis())
// .build();
// gamesRepository.save(newGame);
// }

// public void atualizarJogo(Long id, GamesDTO updateGamesDTO) {
// GamesModel game = gamesRepository.findById(id)
// .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado!"));

// game.setNome(updateGamesDTO.nome());
// game.setDataJogo(updateGamesDTO.dataJogo());
// game.setLocal(updateGamesDTO.local());
// game.setDescricao(updateGamesDTO.descricao());
// game.setSituacao(updateGamesDTO.situacao());
// game.setResponsaveis(updateGamesDTO.responsaveis());

// gamesRepository.save(game);
// }

// public List<GamesModel> listarJogos() {// retirar depois
// return gamesRepository.findAll();
// }

// public boolean removerJogo(Long id) {
// if (gamesRepository.findById(id).isPresent()) {
// gamesRepository.deleteById(id);
// return true;
// }
// return false;
// }
// }
