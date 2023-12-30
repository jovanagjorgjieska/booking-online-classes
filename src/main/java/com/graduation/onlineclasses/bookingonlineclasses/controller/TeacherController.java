package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.CourseDTO;
import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.TeacherDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<BaseUser>> getAllTeachers() {
        return ResponseEntity.ok(this.teacherService.getAllTeachers("TEACHER"));
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<BaseUser> fetchTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.teacherService.getUserById(teacherId));
    }

    @GetMapping("/email/{teacherEmail}")
    public ResponseEntity<BaseUser> fetchTeacherById(@PathVariable String teacherEmail) {
        return ResponseEntity.ok(this.teacherService.getUserByEmail(teacherEmail));
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<BaseUser> editTeacher(@PathVariable Long teacherId, @RequestBody TeacherDTO teacherDTO) {
        BaseUser teacher = this.teacherService.updateUser(teacherId, userMapper
                .mapFromTeacherDtoToUser(teacherDTO));

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(teacher.getUserId())
                .toUri();
        return ResponseEntity.created(location).body(teacher);
    }

    @DeleteMapping("/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long teacherId) {
        this.teacherService.deleteUser(teacherId);
        return new ResponseEntity<>("Teacher deleted successfully!", HttpStatus.NO_CONTENT);
    }


    @GetMapping("/{teacherId}/courses")
    public ResponseEntity<List<Course>> getTeacherCourses(@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.teacherService.getAllCoursesForTeacher(teacherId));
    }

    @GetMapping("/{teacherId}/courses/{courseId}")
    public ResponseEntity<Course> getCourse(@PathVariable Long teacherId, @PathVariable Long courseId) {
        return ResponseEntity.ok(this.teacherService.getTeachersCourse(teacherId, courseId));
    }

    @PostMapping("/{teacherId}/courses")
    public ResponseEntity<Course> addCourse(@PathVariable Long teacherId, @RequestBody CourseDTO courseDTO) {
        Course addedCourse = this.courseService.addCourse(teacherId, courseDTO);

        return new ResponseEntity<>(addedCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{teacherId}/courses/{courseId}")
    public ResponseEntity<Course> editTeachersCourse (@PathVariable Long teacherId, @PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        Course course = this.courseService.editCourse(teacherId, courseId, courseDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getCourseId())
                .toUri();
        return ResponseEntity.created(location).body(course);
    }


    @DeleteMapping("/{teacherId}/courses/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long teacherId, @PathVariable Long courseId) {
        this.courseService.deleteCourse(courseId);
        return new ResponseEntity<>("Course deleted successfully!", HttpStatus.NO_CONTENT);
    }
}
