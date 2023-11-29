package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.CourseDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        return this.courseRepository.findAllByTeacherId(teacherId);
    }

    public Optional<Course> getCourse(Long courseId) {
        return this.courseRepository.findById(courseId);
    }

    public void addCourse(Long teacherId, CourseDTO courseDTO) {
        Course course = new Course();
        course.setTeacherId(teacherId);
        course.setCourseName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setBookedPositions(0);
        course.setAvailablePositions(courseDTO.getAvailablePositions());
        course.setPrice(course.getPrice());

        this.courseRepository.save(course);
    }

    public Optional<Course> editCourse(Long courseId) {
        return Optional.of(new Course());
    }

    public Course deleteCourse(Long courseId) {
        return new Course();
    }
}
