package com.example.projeto.seguranca.dto;

import com.example.projeto.seguranca.enums.Role; 
public record CreateUserDTO(String email, String senha, String nome, int operador, Role role) {
}