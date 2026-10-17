package com.iheb.tracker.service;

import com.iheb.tracker.dto.LoginRequest;
import com.iheb.tracker.dto.LoginResponse;
import com.iheb.tracker.dto.RegisterRequest;
import com.iheb.tracker.dto.UserResponse;
import com.iheb.tracker.exception.EmailAlreadyUsedException;
import com.iheb.tracker.exception.InvalidCredentialsException;
import com.iheb.tracker.model.AppUser;
import com.iheb.tracker.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(AppUserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public UserResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyUsedException("This email is already registered");
        }

        AppUser user = new AppUser();
        user.setFullName(request.fullName().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));

        AppUser saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getFullName(), saved.getEmail());
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.email().trim().toLowerCase();

        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = tokenService.generateToken(user);
        return new LoginResponse(token, new UserResponse(user.getId(), user.getFullName(), user.getEmail()));
    }
}