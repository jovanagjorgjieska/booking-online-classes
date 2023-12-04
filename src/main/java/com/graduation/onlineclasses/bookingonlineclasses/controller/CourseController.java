package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(this.courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable Long courseId) {
        try {
            Course fetchedCourse = this.courseService.getCourse(courseId)
                    .orElseThrow(() -> new CourseNotFoundException(courseId));
            return ResponseEntity.ok(fetchedCourse);
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}