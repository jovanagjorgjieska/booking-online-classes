package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.RegisterRequest;
import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.LoginRequest;
import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.LoginResponse;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.service.AuthService;
import com.graduation.onlineclasses.bookingonlineclasses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{email}")
    public ResponseEntity<BaseUser> fetchUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(this.userService.getUserByEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest user) {
            BaseUser createdStudent = this.userService.createUser(user);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
}
