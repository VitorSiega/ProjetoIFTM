package com.example.projeto.presenca.model;


import java.time.LocalDate;

import com.example.projeto.login.model.ModelUser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "presencas")
@Entity
public class PresencaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")  // Nome correto da coluna de chave estrangeira
    private ModelUser user;

    private LocalDate data;  // Data da presença
    
    private String status;   // "Presente" ou "Falta"

}
