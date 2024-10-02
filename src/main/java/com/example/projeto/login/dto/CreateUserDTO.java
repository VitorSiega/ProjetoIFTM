package com.example.projeto.login.dto;

import com.example.projeto.login.enums.Role;

public record CreateUserDTO(String email, String senha, String nome, int operador, Role role) {

}
