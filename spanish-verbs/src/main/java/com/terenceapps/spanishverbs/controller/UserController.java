package com.terenceapps.spanishverbs.controller;

import com.terenceapps.spanishverbs.config.JwtTokenUtil;
import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
public class UserController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authManager;
    private final UserService userService;

    public UserController(JwtTokenUtil jwtTokenUtil, AuthenticationManager authManager, UserService userService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authManager = authManager;
        this.userService = userService;
    }

    @GetMapping("/verify-token")
    public ResponseEntity<Boolean> verifyToken(@RequestParam String token) {
        boolean isTokenValid = jwtTokenUtil.validate(token);
        return new ResponseEntity<>(isTokenValid, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@Validated(User.Signin.class) @RequestBody User user) {
        authManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(), user.getPassword()
                        )
                );

        BigDecimal userId = userService.getUserIdFromEmail(user.getEmail());
        user.setId(userId);

        return ResponseEntity.ok().body(jwtTokenUtil.generateToken(user));
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@Validated(User.Register.class) @RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }
}
