package com.example.projeto.configuracoes;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private final JwtDecoder jwtDecoder;

    public TokenController(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @PostMapping("/api/validatetoken")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Verifica se o cabeçalho de autorização está presente e inicia com "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " do início

        try {
            // Decodifica o token
            @SuppressWarnings("unused")
            Jwt jwt = jwtDecoder.decode(token);
            // Se necessary, você pode fazer verificações adicionais no token aqui

            return ResponseEntity.ok().build(); // Token é válido
        } catch (JwtException e) {
            // Se o token não for válido, retorna 401 Unauthorized
            return ResponseEntity.status(401).build();
        }
    }
}
