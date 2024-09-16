package com.example.projeto.dto;

import com.example.projeto.enums.Role; 
public record CreateUserDTO(String email, String password, Role role) {
}