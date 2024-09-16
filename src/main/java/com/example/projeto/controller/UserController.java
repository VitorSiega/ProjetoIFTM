package com.example.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.dto.CreateUserDTO;
import com.example.projeto.dto.JwtTokenDTO;
import com.example.projeto.dto.LoginUserDTO;
import com.example.projeto.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDTO> loginUsuario(@RequestBody LoginUserDTO loginUserDto) {
        JwtTokenDTO token = userService.autenticarUsuario(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody CreateUserDTO createUserDTO) {
        try {
            userService.salvarUsuario(createUserDTO);
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
}
