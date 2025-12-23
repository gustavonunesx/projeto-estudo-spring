package com.projeto_estudo_spring.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    public JwtAuthFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Pega o header "Authorization" da requisição HTTP
        String authHeader = request.getHeader("Authorization");
        
        // 2. Se NÃO tem header OU NÃO começa com "Bearer ", pula a autenticação JWT
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // IMPORTANTE: retorna aqui para não executar o resto
        }
        
        // 3. Se chegou aqui, existe um token. Vamos processar!
        try {
            // Remove "Bearer " (7 caracteres) para pegar só o token
            String token = authHeader.substring(7);
            
            // Extrai o username do token
            String username = JwtUtil.extractUsername(token);

            // 4. Se tem username E ainda não está autenticado
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Carrega os dados do usuário do banco
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                // Valida se o token é válido
                if (JwtUtil.validateToken(token)) {
                    // Cria o objeto de autenticação
                    UsernamePasswordAuthenticationToken authToken = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, 
                            null, 
                            userDetails.getAuthorities()
                        );
                    
                    // Define o usuário como autenticado no contexto do Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Se der qualquer erro no processamento do token, apenas loga
            // e deixa a requisição continuar (sem autenticação)
            System.out.println("Erro ao processar token JWT: " + e.getMessage());
        }
        
        // 5. SEMPRE continua a cadeia de filtros (seja com ou sem autenticação)
        filterChain.doFilter(request, response);
    }
}