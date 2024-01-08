package com.apptool.user.config.security.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        if (token != null) {
         
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        // Implémentez la logique pour extraire le token de l'en-tête d'autorisation
        // Par exemple, si le token est dans l'en-tête "Authorization", vous pouvez faire quelque chose comme :
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            // Si le token n'est pas dans l'en-tête, essayez de le récupérer du stockage local
            return retrieveTokenFromLocalStorage();
        }
    }

    private String retrieveTokenFromLocalStorage() {
        // Implémentez la logique pour récupérer le token du stockage local
        // par exemple :
        // return localStorage.getItem('token');
        return null;
    }
}
