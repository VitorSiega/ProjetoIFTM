package com.example.projeto.login.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.login.dto.CreateUserDTO;
import com.example.projeto.login.dto.LoginUserDTO;
import com.example.projeto.login.errorStatus.ErrorResponse;
import com.example.projeto.login.model.ModelUser;
import com.example.projeto.login.model.ReturnModel;
import com.example.projeto.login.repository.UserRepository;
import com.example.projeto.login.service.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @SuppressWarnings("FieldMayBeFinal")
    private ReturnModel returnModel = new ReturnModel();

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUsuario(@RequestBody LoginUserDTO loginUserDto) {
        try {
            Optional<ModelUser> userOptional = userRepository.findByEmail(loginUserDto.email());
            returnModel.setTokenDTO(userService.autenticarUsuario(loginUserDto));
            returnModel.setIdUsuario(String.valueOf(userOptional.get().getId()));
            return new ResponseEntity<>(returnModel, HttpStatus.OK);
        } catch (AuthenticationException e) {
            // Autenticação falhou, retornar status 401
            return new ResponseEntity<>(new ErrorResponse("Credenciais inválidas"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // Outros erros
            return new ResponseEntity<>(new ErrorResponse("Erro interno do servidor"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/admin/cadastro")
    public ResponseEntity<String> cadastrarUsuario(@RequestBody CreateUserDTO createUserDTO) {
        try {

            if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Já existe uma pessoa usando esse email!");
            }
            if (createUserDTO.operador() != 0 && userRepository.findByOperador(createUserDTO.operador()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número do operador já existe");
            }

            if (createUserDTO.operador() < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Número do operador inválido");
            }
            if (userRepository.findByCpf(createUserDTO.cpf()).isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já cadastrado");
            }
            if (createUserDTO.senha().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Coloque uma senha");
            }

            userService.salvarUsuario(createUserDTO);
            return ResponseEntity.status(201).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/admin/atualizar/{id}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable Long id, @RequestBody CreateUserDTO createUserDTO) {
        return userService.atualizarUsuario(id, createUserDTO);
    }

    @DeleteMapping("/admin/remover/{id}")
    public ResponseEntity<String> removerUsuario(@PathVariable Long id) {
        if (userService.removerUsuario(id)) {
            return ResponseEntity.status(200).body(null);
        } else {
            return ResponseEntity.status(500).body("Usuário não encontrado");
        }
    }

    @GetMapping("/admin/listar") // retirar depois
    public ResponseEntity<List<ModelUser>> listarUsuarios() {
        return ResponseEntity.status(200).body(userService.listarLogins());
    }

    @GetMapping("/user/listar")
    public ModelUser getMethodName(@RequestParam("usuario") String id) {
        Long IDD = Long.valueOf(id);
        ModelUser user = userRepository.findById(IDD)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return user;
    }

}
