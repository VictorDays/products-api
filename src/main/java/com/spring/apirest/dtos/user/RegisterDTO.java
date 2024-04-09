package com.spring.apirest.dtos.user;

import com.spring.apirest.models.users.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
