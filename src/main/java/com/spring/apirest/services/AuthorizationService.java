package com.spring.apirest.services;

import com.spring.apirest.models.users.User;
import com.spring.apirest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscar o usuário pelo nome de usuário
        User user = (User) userRepository.findByLogin(username);

        // Verificar se o usuário foi encontrado
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Retornar o usuário encontrado
        return user;
    }
}

