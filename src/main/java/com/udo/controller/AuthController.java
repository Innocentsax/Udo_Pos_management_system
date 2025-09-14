package com.udo.controller;

import com.udo.exceptions.UserException;
import com.udo.payload.DTO.UserDTO;
import com.udo.payload.response.AuthResponse;
import com.udo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signupHandler(@RequestBody UserDTO userDTO) throws UserException {
        return ResponseEntity.ok(authService.signup(userDTO));
    }
}
