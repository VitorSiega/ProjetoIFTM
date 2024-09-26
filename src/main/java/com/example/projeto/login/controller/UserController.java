package com.example.projeto.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.login.dto.CreateUserDTO;
import com.example.projeto.login.dto.JwtTokenDTO;
import com.example.projeto.login.dto.LoginUserDTO;
import com.example.projeto.login.errorStatus.ErrorResponse;
import com.example.projeto.login.model.ModelUser;
import com.example.projeto.login.repository.UserRepository;
import com.example.projeto.login.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody CreateUserDTO createUserDTO) {
        try {
            if (createUserDTO.email().isEmpty() || createUserDTO.senha().isEmpty() || createUserDTO.nome().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preencha todos os dados");
            }
            if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Já existe uma pessoa usando esse email e senha!");
            }
            if (createUserDTO.operador() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número do operador inválido");
            }
            if (userRepository.findByOperador(createUserDTO.operador()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Operador ja existente");
            }
            userService.salvarUsuario(createUserDTO);
            return ResponseEntity.status(201).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @RequestBody CreateUserDTO createUserDTO) {
        try {
            if (createUserDTO.email().isEmpty() || createUserDTO.senha().isEmpty() || createUserDTO.nome().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Preencha todos os dados");
            }
            if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Já existe uma pessoa usando esse email e senha!");
            }
            if (createUserDTO.operador() <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número do operador inválido");
            }
            if (userRepository.findByOperador(createUserDTO.operador()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Operador ja existente");
            }
            userService.atualizarUsuario(id, createUserDTO);
            return ResponseEntity.status(201).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<String> removerUsuario(@PathVariable Long id){
        userService.removerUsuario(id);
        return ResponseEntity.status(200).body(null);
    }


    @GetMapping("/listar") // retirar depois
    public ResponseEntity<List<ModelUser>> listarUsuarios() {
        return ResponseEntity.status(200).body(userService.listarLogins());
    }
}
