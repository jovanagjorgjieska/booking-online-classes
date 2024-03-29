package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.CourseDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Review;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.CourseCategory;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.CourseType;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UploadStatus;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<Course> getAllCoursesByName(String courseName) {
        return this.courseRepository.findAllByCourseNameContainingIgnoreCase(courseName);
    }

    public Course getCourse(Long courseId) {
        return this.courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    public Course addCourse(Long teacherId, CourseDTO courseDTO) {
        Course course = new Course();
        BaseUser teacher = this.teacherService.getUserById(teacherId);

        course.setTeacher(teacher);
        course.setCourseName(courseDTO.getCourseName());
        course.setDescription(courseDTO.getDescription());
        course.setDetails(courseDTO.getDetails());
        course.setTotalPositions(courseDTO.getTotalPositions());
        course.setAvailablePositions(courseDTO.getTotalPositions());
        course.setCourseType(CourseType.valueOf(courseDTO.getCourseType()));
        course.setCourseCategory(CourseCategory.valueOf(courseDTO.getCourseCategory()));
        course.setPrice((double) courseDTO.getPrice());
        course.setCourseUploadStatus(UploadStatus.PENDING);

        this.courseRepository.save(course);

        return course;
    }

    //TODO: Add validation here, check if the course belongs to the teacher
    public Course editCourse(Long teacherId, Long courseId, CourseDTO courseDTO) {
        Optional<Course> course = this.courseRepository.findById(courseId);

        if (course.isPresent()) {
            if (courseDTO.getCourseName() != null)
                course.get().setCourseName(courseDTO.getCourseName());
            if (courseDTO.getDescription() != null)
                course.get().setDescription(courseDTO.getDescription());
            if (courseDTO.getDetails() != null)
                course.get().setDetails(courseDTO.getDetails());
            if (courseDTO.getPrice() != null)
                course.get().setPrice((double) courseDTO.getPrice());
            if (courseDTO.getTotalPositions() != null)
                course.get().setTotalPositions(courseDTO.getTotalPositions());
            if (courseDTO.getCourseType() != null)
                course.get().setCourseType(CourseType.valueOf(courseDTO.getCourseType()));
            if (courseDTO.getCourseCategory() != null)
                course.get().setCourseCategory(CourseCategory.valueOf(courseDTO.getCourseCategory()));
            return this.courseRepository.save(course.get());
        } else {
            throw new CourseNotFoundException(courseId);
        }
    }

    //TODO: Add validation here, check if the course belongs to the teacher
    public void deleteCourse(Long courseId) {
        Optional<Course> course = this.courseRepository.findById(courseId);
        if (course.isPresent()) {
            this.courseRepository.delete(course.get());
        } else {
            throw new CourseNotFoundException(courseId);
        }
    }

    public void changeCourseAvailablePositions(Long courseId, Integer availablePositions) {
        Course course = this.getCourse(courseId);

        course.setAvailablePositions(availablePositions);
        this.courseRepository.save(course);
    }

    public List<Course> filterByCategoryAndType(String category, String type) {
        try {
            CourseCategory courseCategory = (category != null) ? CourseCategory.valueOf(category) : null;
            CourseType courseType = (type != null) ? CourseType.valueOf(type) : null;

            if (courseCategory != null && courseType != null) {
                return this.courseRepository.findAllByCourseCategoryAndCourseType(courseCategory, courseType);
            } else if (courseCategory != null) {
                return this.courseRepository.findAllByCourseCategory(courseCategory);
            } else if (courseType != null) {
                return this.courseRepository.findAllByCourseType(courseType);
            } else {
                return this.courseRepository.findAll();
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid CourseCategory or CourseType value");
        }
    }

    public void updateCourseRating(Long courseId) {
        Course course = this.getCourse(courseId);

        List<Review> courseReviews = course.getReviews();
        float totalScore = 0;
        int reviewNumber = 0;

        for (Review r : courseReviews) {
            totalScore += r.getScore();
            reviewNumber ++;
        }

        course.setRating(totalScore / reviewNumber);
        this.courseRepository.save(course);
    }
}
