package com.paolo.lucascaffe.controller;


import com.paolo.lucascaffe.dto.AuthResponse;
import com.paolo.lucascaffe.dto.RegisterRequest;
import com.paolo.lucascaffe.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Registro y login de usuarios")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar nuevo usuario", description = "Crea un usuario y devuelve un token JWT")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @Operation(summary = "Iniciar Sesion", description = "Autentica al usuario y devuelve un toke JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestParam String email,
            @RequestParam String password){
        return ResponseEntity.ok(authService.login(email,password));
    }

}
