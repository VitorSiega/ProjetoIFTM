package com.example.projeto.login.dto;

import java.time.LocalDate;

import com.example.projeto.login.enums.Role;

public record CreateUserDTO(
        String email,
        String nome,
        String senha,
        int operador,
        String cpf,
        LocalDate dataNascimento,
        String telefone,
        String telefoneEmergencia,
        String tipoSanguineo,
        String ocupacao,
        String statusOperador,
        Role role) {

}
