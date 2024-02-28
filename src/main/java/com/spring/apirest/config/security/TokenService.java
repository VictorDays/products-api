package com.spring.apirest.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.spring.apirest.models.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    //Essa anotação é usada para injetar o valor da propriedade api.security.token.secret definida no arquivo de propriedades.
    @Value("${api.security.token.secret}")
    private String secret;

    // Método para gerar um token JWT com base nas informações do usuário.
    public String generateToken(User user){
        try{
            // Cria um algoritmo de criptografia usando o segredo fornecido.
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Cria o token JWT com algumas informações específicas do usuário, como emissor, assunto e data de expiração.
            String token =  JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generationExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException exception){
            throw new RuntimeException("Error while generation token", exception);
        }
    }

    // Método para validar e extrair o login de um token JWT.
    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // Verifica a validade do token JWT e extrai o login do usuário.
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return " ";
        }

    }

    // Retorna uma instância de Instant que representa a data de expiração do token.
    private Instant generationExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
