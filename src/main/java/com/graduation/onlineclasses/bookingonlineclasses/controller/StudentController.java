package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.StudentDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Student;
import com.graduation.onlineclasses.bookingonlineclasses.mapper.StudentMapper;
import com.graduation.onlineclasses.bookingonlineclasses.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> fetchStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(this.studentService.getUserById(studentId));
    }

    @GetMapping("/email/{studentEmail}")
    public ResponseEntity<Student> fetchStudent(@PathVariable String studentEmail) {
        return ResponseEntity.ok(this.studentService.getUserByEmail(studentEmail));
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> editStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO) {
        Student student = this.studentService.updateUser(studentId, studentMapper.mapFromStudentDtoToStudent(studentDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getStudentId())
                .toUri();
        return ResponseEntity.created(location).body(student);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        this.studentService.deleteUser(studentId);
        return new ResponseEntity<>("Student deleted successfully!", HttpStatus.NO_CONTENT);
    }
}
