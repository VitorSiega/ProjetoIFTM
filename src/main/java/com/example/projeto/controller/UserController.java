package com.example.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.dto.CreateUserDTO;
import com.example.projeto.dto.JwtTokenDTO;
import com.example.projeto.dto.LoginUserDTO;
import com.example.projeto.errorStatus.ErrorResponse;
import com.example.projeto.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
public ResponseEntity<?> loginUsuario(@RequestBody LoginUserDTO loginUserDto) {
    try {
        JwtTokenDTO token = userService.autenticarUsuario(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    } catch (AuthenticationException e) {
        // Autenticação falhou, retornar status 401
        return new ResponseEntity<>(new ErrorResponse("Credenciais inválidas"), HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
        // Outros erros
        return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody CreateUserDTO createUserDTO) {

        try {
            if(createUserDTO.email().isEmpty() || createUserDTO.senha().isEmpty() || createUserDTO.nome().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preencha todos os dados");
            }
            
            userService.salvarUsuario(createUserDTO);
            return ResponseEntity.status(201).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuário: " + e.getMessage());
        }

    }
}
