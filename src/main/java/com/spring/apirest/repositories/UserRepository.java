package com.spring.apirest.repositories;

import com.spring.apirest.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Método para buscar um usuário com base no login.
    // Retorna UserDetails, que é uma interface do Spring Security representando os detalhes do usuário.
    UserDetails findByLogin(String login);
}
