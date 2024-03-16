package de.ju.springsecurityjwttemplate.controller;

import de.ju.springsecurityjwttemplate.entity.AppUser;
import de.ju.springsecurityjwttemplate.model.AuthRequest;
import de.ju.springsecurityjwttemplate.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        Optional<AppUser> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User already exists.");
        }

        userRepository.save(
                AppUser.builder()
                        .email(request.email())
                        .password(passwordEncoder.encode(request.password()))
                        .roles(request.roles())
                        .build()
        );

        return ResponseEntity.ok("User successfully registered.");
    }
}
