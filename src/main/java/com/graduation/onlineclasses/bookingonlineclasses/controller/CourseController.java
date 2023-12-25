package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/title")
    public ResponseEntity<List<Course>> getAllCoursesByName(@RequestParam String courseName) {
        return ResponseEntity.ok(this.courseService.getAllCoursesByName(courseName));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(this.courseService.getCourse(courseId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Course>> filterCourses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type) {
        try {
            List<Course> filteredCourses = this.courseService.filterByCategoryAndType(category, type);
            return ResponseEntity.ok(filteredCourses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(List.of());
        }
    }
}
