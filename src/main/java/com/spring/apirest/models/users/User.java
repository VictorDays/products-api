package com.spring.apirest.models.users;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_USER")
public class User implements UserDetails {
    //UserDetails é uma interface fornecida pelo Spring Security que representa os detalhes de um usuário
    //do sistema. Esta interface define métodos para obter informações sobre o usuário,
    //como nome de usuário, senha, autorizações (ou papéis) e se a conta do usuário está habilitada.
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUser;

    private String login;

    private String passaword;

    private UserRole role;

    // Construtor com argumentos
    public User(String login, String passaword, UserRole role) {
        this.login = login;
        this.passaword = passaword;
        this.role = role;
    }
    // Construtor padrão sem argumentos
    public User() {
    }

    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassaword() {
        return passaword;
    }

    public void setPassaword(String passaword) {
        this.passaword = passaword;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    //Metodos UserDetails:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_USER"));

        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.passaword;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
