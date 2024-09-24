package com.example.projeto.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.projeto.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserAuthenticationFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);

    private final JwtTokenService jwtTokenService;
    private final UserDetailsServiceImpl userDetailsService;

    public UserAuthenticationFilter(JwtTokenService jwtTokenService, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
    
        String header = httpRequest.getHeader("Authorization");
        logger.debug("Header Authorization: " + header);
    
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // Remove "Bearer " prefix
    
            if (token.isEmpty()) {
                logger.warn("Token JWT está vazio");
                chain.doFilter(request, response);
                return;
            }
    
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    String username = jwtTokenService.pegarToken(token);
    
                    if (username != null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
                        if (userDetails != null) {
                            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        } else {
                            logger.warn("Detalhes do usuário não encontrados para: " + username);
                        }
                    }
                } catch (JWTVerificationException e) {
                    logger.error("Erro de verificação de token: " + e.getMessage());
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                } catch (Exception e) {
                    logger.error("Erro inesperado: " + e.getMessage());
                    httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    return;
                }
            }
        } else {
            logger.warn("Token não encontrado ou inválido no cabeçalho");
        }
    
        chain.doFilter(request, response);
    }
}
