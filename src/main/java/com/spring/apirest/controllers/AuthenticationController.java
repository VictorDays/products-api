package com.spring.apirest.controllers;

import com.spring.apirest.config.security.TokenService;
import com.spring.apirest.dtos.AuthenticationDTO;

import com.spring.apirest.dtos.LoginResponseDTO;
import com.spring.apirest.dtos.RegisterDTO;
import com.spring.apirest.models.users.User;
import com.spring.apirest.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping(value ="/auth/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        // Verificar login e senha do usuário
        var usaernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        // Autenticar usuario
        var auth = this.authenticationManager.authenticate(usaernamePassword);

        // Gerando o Token
        var token = tokenService.generateToken((User) auth.getPrincipal());

        // Retorna uma resposta de sucesso 200 (OK) indicando que o usuário foi registrado com sucesso.
        return ResponseEntity.ok( new LoginResponseDTO(token));
    }
    @PostMapping(value ="/auth/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        // Se um usuário com o mesmo login já existir, retorna uma resposta de erro 400 (Bad Request).
        if (this.userRepository.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();

        // Criptografa a senha fornecida no DTO usando o algoritmo BCrypt.
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        var newUser = new User(registerDTO.login(), registerDTO.password(), registerDTO.role());

        this.userRepository.save(newUser);

        // Retorna uma resposta de sucesso 200 (OK) indicando que o usuário foi registrado com sucesso.
        return ResponseEntity.ok().build();
    }
}
