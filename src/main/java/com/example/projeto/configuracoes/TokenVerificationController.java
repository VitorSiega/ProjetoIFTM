package com.example.projeto.configuracoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.projeto.login.security.JwtTokenService;

@RestController
public class TokenVerificationController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/api/token/verify")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token não fornecido ou formato inválido.");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        try {
            // Tenta pegar o sujeito (username) do token
            String username = jwtTokenService.pegarToken(token);
            return ResponseEntity.ok("Token válido para o usuário: " + username);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Token inválido ou expirado: " + e.getMessage());
        }
    }
}
