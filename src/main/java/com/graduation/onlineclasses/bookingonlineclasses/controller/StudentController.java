package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.StudentDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.mapper.UserMapper;
import com.graduation.onlineclasses.bookingonlineclasses.security.UserPrincipal;
import com.graduation.onlineclasses.bookingonlineclasses.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final UserMapper userMapper;

    @GetMapping("/{studentId}")
    public ResponseEntity<BaseUser> fetchStudent(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long studentId) {
        return ResponseEntity.ok(this.studentService.getUserById(studentId));
    }

    @GetMapping("/email/{studentEmail}")
    public ResponseEntity<BaseUser> fetchStudent(@PathVariable String studentEmail) {
        return ResponseEntity.ok(this.studentService.getUserByEmail(studentEmail));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<BaseUser> editStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        BaseUser student = this.studentService.updateUser(studentId, userMapper.mapFromStudentDtoToUser(studentDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getUserId())
                .toUri();
        return ResponseEntity.created(location).body(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        this.studentService.deleteUser(studentId);
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.NO_CONTENT);
    }
}
