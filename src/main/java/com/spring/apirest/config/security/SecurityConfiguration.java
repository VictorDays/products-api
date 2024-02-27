package com.spring.apirest.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    //Corrente de filtros de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //criando uma plitica de sessão em que não guardamos o estado
                .authorizeHttpRequests(authorize -> authorize //Indicando quais as requisiçoes HTTP serão autorizadas
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/products").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();
    }

    //Este método cria e retorna um bean do tipo AuthenticationManager usando uma instância de AuthenticationConfiguration.
    //O AuthenticationManager é usado para autenticar solicitações de autenticação,
    //como nome de usuário e senha, e é fundamental para o Spring Security.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    //Metodo para criptografias
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Retorna uma instância de SCryptPasswordEncoder do Spring Security para criptografar senhas de forma segura, utilizando o algoritmo SCrypt.
    }

}