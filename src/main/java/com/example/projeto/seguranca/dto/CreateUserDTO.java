package com.example.projeto.dto;

import com.example.projeto.enums.Role; 
public record CreateUserDTO(String email, String senha, String nome, int operador, Role role) {
}