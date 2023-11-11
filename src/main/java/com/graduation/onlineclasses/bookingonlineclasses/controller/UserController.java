package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UserRole;
import com.graduation.onlineclasses.bookingonlineclasses.service.StudentService;
import com.graduation.onlineclasses.bookingonlineclasses.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final TeacherService teacherService;
    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody BaseUser user) {
        if (user.getUserRole().equals(UserRole.STUDENT)) {
            this.studentService.createUser(user);
        } else {
            this.teacherService.createUser(user);
        }
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}
