package com.spring.apirest.controllers;

import com.spring.apirest.dtos.AuthenticationDTO;

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

    @PostMapping(value ="/auth/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        //verificar login e senha do usuário
        var usaernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());

        //autenticar usuario
        var auth = this.authenticationManager.authenticate(usaernamePassword);

        return ResponseEntity.ok().build();
    }
    @PostMapping(value ="/auth/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO){
        if (this.userRepository.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        var newUser = new User(registerDTO.login(), registerDTO.password(), registerDTO.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
