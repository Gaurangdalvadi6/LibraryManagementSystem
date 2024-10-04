package com.manage.library.controller;

import com.manage.library.dto.AuthenticationRequest;
import com.manage.library.dto.AuthenticationResponse;
import com.manage.library.dto.SignUpRequest;
import com.manage.library.dto.UserDto;
import com.manage.library.payload.APIResponse;
import com.manage.library.repository.UserRepository;
import com.manage.library.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

//    @PostMapping("/signup")
//    public ResponseEntity<APIResponse<UserDto>> signUpUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if (authService.hasUserWithEmail(signUpRequest.getEmail())) {
//            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
//                    .body(new APIResponse<>(null, "User already exists with this email", HttpStatus.NOT_ACCEPTABLE));
//        }
//        UserDto createdUserDto = authService.signUpUser(signUpRequest);
//        if (createdUserDto == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new APIResponse<>(null, "User not created", HttpStatus.BAD_REQUEST));
//        }
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new APIResponse<>(createdUserDto, null, HttpStatus.CREATED));
//    }

    @PostMapping("/other/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpRequest signUpRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = authService.signUpUser(signUpRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }


    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }

}