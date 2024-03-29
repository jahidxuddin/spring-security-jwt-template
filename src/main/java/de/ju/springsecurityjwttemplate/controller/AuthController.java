package de.ju.springsecurityjwttemplate.controller;

import de.ju.springsecurityjwttemplate.entity.AppUser;
import de.ju.springsecurityjwttemplate.model.AuthLoginRequest;
import de.ju.springsecurityjwttemplate.model.AuthRegisterRequest;
import de.ju.springsecurityjwttemplate.model.AuthResponse;
import de.ju.springsecurityjwttemplate.repository.AppUserRepository;
import de.ju.springsecurityjwttemplate.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRegisterRequest request) {
        Optional<AppUser> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        AppUser user = AppUser.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(request.roles())
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest request) {
        Optional<AppUser> userOptional = userRepository.findByEmail(request.email());

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        String token = jwtService.generateToken(userOptional.get());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
