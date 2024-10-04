package com.manage.library.dto;

import com.manage.library.entity.Role;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;

    private Long userId;

    private Role role;
}