package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.CourseDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TeacherService teacherService;

    //TODO: Change the implementation of this method. Maybe it's better to fetch the courses from Teacher entity
    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        Optional<Teacher> teacher = this.teacherService.getUserById(teacherId);

        if (teacher.isPresent()) {
            return this.courseRepository.findAllByTeacher(teacher.get());
        } else {
            return new ArrayList<>();
        }
    }

    public Optional<Course> getCourse(Long courseId) {
        return this.courseRepository.findById(courseId);
    }

    public Course addCourse(Long teacherId, CourseDTO courseDTO) {
        Course course = new Course();
        Optional<Teacher> teacher = this.teacherService.getUserById(teacherId);

        teacher.ifPresent(course::setTeacher);
        course.setCourseName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setBookedPositions(0);
        course.setAvailablePositions(courseDTO.getAvailablePositions());
        course.setPrice(courseDTO.getPrice());

        this.courseRepository.save(course);

        return course;
    }

    public Optional<Course> editCourse(Long courseId) {
        return Optional.of(new Course());
    }

    public Course deleteCourse(Long courseId) {
        return new Course();
    }
}
