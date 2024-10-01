package com.example.projeto.login.dto;

import java.util.List;

import javax.management.relation.Role;

public record UserDTO(Long id, String email, List<Role> roles) {

}
