package com.manage.library.service.auth;

import com.manage.library.dto.AuthenticationRequest;
import com.manage.library.dto.AuthenticationResponse;
import com.manage.library.dto.SignUpRequest;
import com.manage.library.dto.UserDto;
import com.manage.library.entity.Role;
import com.manage.library.entity.User;
import com.manage.library.jwt.UserService;
import com.manage.library.repository.UserRepository;
import com.manage.library.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> optionalUser = userRepository.findByRole(Role.ADMIN);
        if (optionalUser.isEmpty()){
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setUsername("Gaurang");
            user.setPassword(new BCryptPasswordEncoder().encode("admin@123"));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully!");
        }else {
            System.out.println("Admin account already exist!");
        }
    }

//    @Override
//    public UserDto signUpUser(SignUpRequest signUpRequest) {
//        User user = new User();
//        user.setEmail(signUpRequest.getEmail());
//        user.setUsername(signUpRequest.getName());
//        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
//        user.setRole(Role.BORROWER);
//        User createdUser = userRepository.save(user);
//        return createdUser.getUserDto();
//    }

    @Override
    public UserDto signUpUser(SignUpRequest signUpRequest, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Role currentUserRole = currentUser.getRole();

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));

        // Set role based on current user's role
        if (currentUserRole == Role.ADMIN) {
            user.setRole(signUpRequest.getRole()); // LIBRARIAN or BORROWER
        } else if (currentUserRole == Role.LIBRARIAN) {
            user.setRole(Role.BORROWER);
        } else {
            throw new RuntimeException("Unauthorized access");
        }

        User createdUser = userRepository.save(user);
        return createdUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or Password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwtToken);
            authenticationResponse.setUserId(optionalUser.get().getId());
            authenticationResponse.setRole(optionalUser.get().getRole());
        }
        return authenticationResponse;
    }

    private List<GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        if (role.equals(Role.ADMIN.getValue())) {
            authorities.add(new SimpleGrantedAuthority("LIBRARIAN"));
            authorities.add(new SimpleGrantedAuthority("BORROWER"));
        } else if (role.equals(Role.LIBRARIAN.getValue())) {
            authorities.add(new SimpleGrantedAuthority("BORROWER"));
        }

        return authorities;
    }
}

