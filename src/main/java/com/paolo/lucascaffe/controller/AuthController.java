package com.paolo.lucascaffe.controller;


import com.paolo.lucascaffe.dto.AuthResponse;
import com.paolo.lucascaffe.dto.RegisterRequest;
import com.paolo.lucascaffe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestParam String email,
            @RequestParam String password){
        return ResponseEntity.ok(authService.login(email,password));
    }

}
