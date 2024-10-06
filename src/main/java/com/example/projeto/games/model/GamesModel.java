package com.example.projeto.games.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "games")
@Entity
public class GamesModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = false, columnDefinition = "VARCHAR(255)")
    private String nome;

    @Column(name = "dataJogo", columnDefinition = "DATE")
    private Date dataJogo;

    @Column(name = "descricao", nullable = false, unique = false, columnDefinition = "VARCHAR(255)")
    private String descricao;

    @Column(name = "local", nullable = false, unique = false, columnDefinition = "VARCHAR(200)")
    private String local;

    @Column(name = "situacao", columnDefinition = "VARCHAR(255)")
    private String situacao;

    @Column(name = "responsaveis", columnDefinition = "VARCHAR(255)")
    private String responsaveis;
}
