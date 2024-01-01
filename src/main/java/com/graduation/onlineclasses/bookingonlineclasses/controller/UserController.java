package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.RegisterRequest;
import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.LoginRequest;
import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.LoginResponse;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UserRole;
import com.graduation.onlineclasses.bookingonlineclasses.service.AuthService;
import com.graduation.onlineclasses.bookingonlineclasses.service.StudentService;
import com.graduation.onlineclasses.bookingonlineclasses.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest user) {
        if (user.getRole().equals(UserRole.STUDENT.toString())) {
            BaseUser createdStudent = this.studentService.createUser(user);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } else {
            BaseUser createdTeacher = this.teacherService.createUser(user);
            return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }
}
