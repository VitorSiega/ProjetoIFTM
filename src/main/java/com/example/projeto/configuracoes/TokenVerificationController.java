package com.example.projeto.configuracoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.projeto.login.security.JwtTokenService;

@RestController
public class TokenVerificationController {

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/api/token/verify")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Verifica se o token foi fornecido e se está no formato correto
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token não fornecido ou formato inválido.");
        }

        // Remove o prefixo "Bearer " do token
        String token = authorizationHeader.substring(7);

        try {
            // Tenta extrair o sujeito (username ou ID do usuário) do token
            String username = jwtTokenService.pegarToken(token);
            return ResponseEntity.ok("Token válido para o usuário: " + username);
        } catch (JWTVerificationException e) {
            // Retorna erro de autenticação se o token for inválido ou expirado
            return ResponseEntity.status(401).body("Token inválido ou expirado: " + e.getMessage());
        }
    }
}
