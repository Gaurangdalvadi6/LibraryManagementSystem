package com.manage.library.service.auth;

import com.manage.library.dto.AuthenticationRequest;
import com.manage.library.dto.AuthenticationResponse;
import com.manage.library.dto.SignUpRequest;
import com.manage.library.dto.UserDto;
import org.springframework.security.core.Authentication;

public interface AuthService {

//    UserDto signUpUser(SignUpRequest signUpRequest);

    UserDto signUpUser(SignUpRequest signUpRequest, Authentication authentication);

    boolean hasUserWithEmail(String email);

    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}