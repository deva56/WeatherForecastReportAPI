package com.weather_forecast_report.service;

import com.weather_forecast_report.contracts.IAuth;
import com.weather_forecast_report.models.dto.auth.LoginRequestDto;
import com.weather_forecast_report.models.dto.auth.RegisterRequestDto;
import com.weather_forecast_report.models.entity.User;
import com.weather_forecast_report.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuth {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<String> registration(@NonNull RegisterRequestDto registerRequestDto) {

        User user = new User(registerRequestDto.getUsername(),
                passwordEncoder.encode(registerRequestDto.getPassword()));

        User userInDb = userRepository.findByUsername(registerRequestDto.getUsername());
        if (userInDb == null) {
            userRepository.save(user);
            return new ResponseEntity<>("User registration successful.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with same username already exists.", HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<String> login(@NonNull LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("Successfully logged in.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong Login Credentials", HttpStatus.FORBIDDEN);
        }
    }
}
