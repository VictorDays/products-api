package com.spring.apirest.config.security;

import com.spring.apirest.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    // Este método é responsável por filtrar as requisições HTTP, onde o token é recuperado e as informações do usuário são extraídas. Ele é chamado para cada requisição HTTP.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token JWT da requisição
        var token = this.recoverToken(request);

        if (token != null) {
            // Valida o token para obter o login do usuário
            var login = tokenService.validateToken(token);
            // Busca as informações do usuário no repositório com base no login obtido do token
            UserDetails user = userRepository.findByLogin(login);

            // Cria uma instância de autenticação do Spring Security com as informações do usuário e suas autorizações
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            // Define a autenticação no contexto de segurança do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Continua o processamento da requisição chamando o próximo filtro na cadeia de filtros
        filterChain.doFilter(request, response);
    }


    // Este método recupera o token JWT da requisição HTTP. Ele verifica se o cabeçalho Authorization está presente
    // na requisição e, se estiver, extrai o token JWT dele, removendo o prefixo "Bearer ".
    private String recoverToken(HttpServletRequest request) {
        // Obtém o valor do cabeçalho Authorization da requisição
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            // Se estiver ausente, retorna null indicando que nenhum token foi encontrado
            return null;
        }

        // Se o cabeçalho Authorization estiver presente, extrai o token JWT dele, removendo o prefixo "Bearer "
        return authHeader.replace("Bearer ", "");
    }

}
