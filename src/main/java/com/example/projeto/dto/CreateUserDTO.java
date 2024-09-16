package com.example.projeto.dto;

import com.example.projeto.enums.Role; 
public record CreateUserDTO(String email, String senha, Role role) {
}