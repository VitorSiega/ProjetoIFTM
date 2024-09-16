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

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                       JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public void salvarUsuario(CreateUserDTO createUserDto) {
        // Cria a role usando o enum diretamente
        ModelRole role = ModelRole.builder()
                .name(createUserDto.role()) // Certifique-se de que role seja um enum
                .build();

        // Cria o novo usuário com a role associada
        ModelUser newUser = ModelUser.builder()
                .email(createUserDto.email())
                .password(passwordEncoder.encode(createUserDto.password()))
                .roles(List.of(role)) // Associa a role ao usuário
                .build();

        userRepository.save(newUser);
    }

    public JwtTokenDTO autenticarUsuario(LoginUserDTO loginUserDto) {
        // Cria o token de autenticação
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        ModelUserDetailsImpl modelUserDetails = (ModelUserDetailsImpl) authentication.getPrincipal();

        // Gera o JWT token
        return new JwtTokenDTO(jwtTokenService.generateToken(modelUserDetails));
    }
}
