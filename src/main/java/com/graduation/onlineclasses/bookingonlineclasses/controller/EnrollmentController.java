package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.EnrollmentDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Enrollment;
import com.graduation.onlineclasses.bookingonlineclasses.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<Enrollment> getEnrollment (@PathVariable Long enrollmentId) {
        return ResponseEntity.ok(this.enrollmentService.getEnrollmentById(enrollmentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsForCourse (@PathVariable Long courseId) {
        return ResponseEntity.ok(this.enrollmentService.getAllEnrollmentsByCourse(courseId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsForStudent (@PathVariable Long studentId) {
        return ResponseEntity.ok(this.enrollmentService.getAllEnrollmentsByStudent(studentId));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsForTeacher (@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.enrollmentService.getAllEnrollmentsByTeacher(teacherId));
    }

    @PostMapping
    public ResponseEntity<Enrollment> enrollToCourse (@RequestBody EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = this.enrollmentService.enrollToCourse(enrollmentDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(enrollment.getEnrollmentId())
                .toUri();
        return ResponseEntity.created(location).body(enrollment);
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<String> cancelEnrollment (@PathVariable Long enrollmentId) {
        this.enrollmentService.cancelEnrollment(enrollmentId);
        return new ResponseEntity<>("Booking cancelled successfully", HttpStatus.NO_CONTENT);
    }
}
