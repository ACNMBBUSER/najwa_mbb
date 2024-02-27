package com.userprofile.profile.controller;

import com.userprofile.profile.model.entity.User;
import com.userprofile.profile.model.response.AuthenticationResponse;
import com.userprofile.profile.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody User request) {
//        return ResponseEntity.ok(authService.register(request));
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/admin_only")
    public ResponseEntity<AuthenticationResponse> adminOnly (@RequestBody User request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
