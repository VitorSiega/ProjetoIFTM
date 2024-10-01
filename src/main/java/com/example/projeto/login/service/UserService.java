package com.example.projeto.login.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projeto.login.dto.CreateUserDTO;
import com.example.projeto.login.dto.JwtTokenDTO;
import com.example.projeto.login.dto.LoginUserDTO;
import com.example.projeto.login.model.ModelRole;
import com.example.projeto.login.model.ModelUser;
import com.example.projeto.login.model.ModelUserDetailsImpl;
import com.example.projeto.login.repository.UserRepository;
import com.example.projeto.login.security.JwtTokenService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public void salvarUsuario(CreateUserDTO createUserDto) {
        // Obtemos ou criamos a role
        ModelRole role = roleService.getOrCreateRole(createUserDto.role());
        // Cria o novo usuário com a role associada
        ModelUser newUser = ModelUser.builder()
                .email(createUserDto.email())
                .senha(passwordEncoder.encode(createUserDto.senha()))
                .nome(createUserDto.nome())
                .operador(createUserDto.operador())
                .roles(List.of(role)) // Associa a role ao usuário
                .build();
        userRepository.save(newUser);
    }

    public JwtTokenDTO autenticarUsuario(LoginUserDTO loginUserDto) {
        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUserDto.email(), loginUserDto.senha());
        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();
        // Gera o JWT token
        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));
    }

    public void atualizarUsuario(Long id, CreateUserDTO updateUserDTO) {
        // Carrega o usuário do banco
        ModelUser userAtual = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        // Atualiza os campos do usuário
        userAtual.setEmail(updateUserDTO.email());
        userAtual.setSenha(passwordEncoder.encode(updateUserDTO.senha()));
        userAtual.setNome(updateUserDTO.nome());
        userAtual.setOperador(updateUserDTO.operador());
        // Atualiza as roles
        userAtual.getRoles().clear(); // Remove as roles antigas
        userAtual.getRoles().add(roleService.getOrCreateRole(updateUserDTO.role())); // Adiciona a nova role
        // Persiste as alterações
        userRepository.save(userAtual);
    }

    public boolean removerUsuario(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ModelUser> listarLogins() {// retirar depois
        return userRepository.findAll();
    }
}