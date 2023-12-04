package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.CourseDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
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

    public List<Course> getAllCourses() {
        return this.courseRepository.findAll();
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

    public Optional<Course> editCourse(Long courseId, CourseDTO courseDTO) {
        Optional<Course> course = this.courseRepository.findById(courseId);

        if (course.isPresent()) {
            if (courseDTO.getCourseName() != null) {
                course.get().setCourseName(courseDTO.getCourseName());
            }
            if (courseDTO.getDescription() != null) {
                course.get().setDescription(courseDTO.getDescription());
            }
            if (courseDTO.getPrice() != null) {
                course.get().setPrice(courseDTO.getPrice());
            }
            if (courseDTO.getAvailablePositions() != null) {
                course.get().setAvailablePositions(courseDTO.getAvailablePositions());
            }

            this.courseRepository.save(course.get());
        }

        return course;
    }

    public void deleteCourse(Long courseId) {
        Optional<Course> course = this.courseRepository.findById(courseId);
        if (course.isPresent()) {
            this.courseRepository.delete(course.get());
        } else {
            throw new CourseNotFoundException(courseId);
        }
    }
}
