package com.manage.library.dto;

import com.manage.library.entity.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String username;

    private String password;

    private Role role;
}
