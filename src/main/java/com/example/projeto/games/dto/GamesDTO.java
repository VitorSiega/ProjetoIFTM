package com.example.projeto.games.dto;

import java.util.Date;

public record GamesDTO(
        Long id,
        String nome,
        Date dataJogo,
        String descricao,
        String local,
        String situacao,
        String responsaveis) {

}
