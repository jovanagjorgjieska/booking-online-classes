package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.CourseDTO;
import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.TeacherDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.exception.TeacherNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.mapper.TeacherMapper;
import com.graduation.onlineclasses.bookingonlineclasses.service.CourseService;
import com.graduation.onlineclasses.bookingonlineclasses.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherMapper teacherMapper;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(this.teacherService.getAllTeachers());
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<?> fetchTeacher(@PathVariable Long teacherId) {
        try {
            Teacher fetchedTeacher = this.teacherService.getUserById(teacherId)
                    .orElseThrow(() -> new TeacherNotFoundException(teacherId));
            return ResponseEntity.ok(fetchedTeacher);
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<?> editTeacher(@PathVariable Long teacherId, @RequestBody TeacherDTO teacherDTO) {
        try {
            Teacher teacher = this.teacherService.updateUser(teacherId, teacherMapper.mapFromTeacherDtoToTeacher(teacherDTO))
                    .orElseThrow(() -> new TeacherNotFoundException(teacherId));

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(teacher.getTeacherId())
                    .toUri();
            return ResponseEntity.created(location).body(teacher);
        } catch (TeacherNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{teacherId}/courses")
    public ResponseEntity<List<Course>> getTeacherCourses(@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.teacherService.getAllCoursesForTeacher(teacherId));
    }

    @GetMapping("/{teacherId}/courses/{courseId}")
    public ResponseEntity<?> getCourse(@PathVariable Long teacherId, @PathVariable Long courseId) {
        try {
            Course fetchedCourse = this.teacherService.getTeachersCourse(teacherId, courseId)
                    .orElseThrow(() -> new CourseNotFoundException(courseId));
            return ResponseEntity.ok(fetchedCourse);
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{teacherId}/courses")
    public ResponseEntity<Course> addCourse(@PathVariable Long teacherId, @RequestBody CourseDTO courseDTO) {
        Course addedCourse = this.courseService.addCourse(teacherId, courseDTO);

        return new ResponseEntity<>(addedCourse, HttpStatus.CREATED);
    }
}
