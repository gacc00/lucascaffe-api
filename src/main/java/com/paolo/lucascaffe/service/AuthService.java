package com.paolo.lucascaffe.service;


import com.paolo.lucascaffe.dto.AuthResponse;
import com.paolo.lucascaffe.dto.RegisterRequest;
import com.paolo.lucascaffe.model.Role;
import com.paolo.lucascaffe.model.User;
import com.paolo.lucascaffe.repository.UserRepository;
import com.paolo.lucascaffe.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request){
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(String email, String password){
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException("Contraseña incorrecta");
        }
        var token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
