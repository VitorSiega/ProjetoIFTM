package com.example.projeto.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.projeto.dto.CreateUserDTO;
import com.example.projeto.dto.JwtTokenDTO;
import com.example.projeto.dto.LoginUserDTO;
import com.example.projeto.model.ModelRole;
import com.example.projeto.model.ModelUser;
import com.example.projeto.model.ModelUserDetailsImpl;
import com.example.projeto.repository.UserRepository;
import com.example.projeto.security.JwtTokenService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService; // Adicione a dependência do RoleService

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService; // Inicialize o RoleService
    }

    public void salvarUsuario(CreateUserDTO createUserDto) {
        // Obtemos ou criamos a role
        ModelRole role = roleService.getOrCreateRole(createUserDto.role());

        // Cria o novo usuário com a role associada
        ModelUser newUser = ModelUser.builder()
                .email(createUserDto.email())
                .senha(passwordEncoder.encode(createUserDto.senha()))
                .roles(List.of(role)) // Associa a role ao usuário
                .build();

        userRepository.save(newUser);
    }

    public JwtTokenDTO autenticarUsuario(LoginUserDTO loginUserDto) {
        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.senha());

        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();

        // Gera o JWT token
        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));
    }
}
