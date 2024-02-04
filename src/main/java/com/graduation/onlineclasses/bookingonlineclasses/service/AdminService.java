package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Review;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UploadStatus;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.exception.ReviewNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.CourseRepository;
import com.graduation.onlineclasses.bookingonlineclasses.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;
    private final CourseService courseService;

    public void approveCourse (Long courseId) {
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        course.setCourseUploadStatus(UploadStatus.APPROVED);
        this.courseRepository.save(course);
    }

    public void denyCourse (Long courseId) {
        Course course = this.courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        course.setCourseUploadStatus(UploadStatus.DENIED);
        this.courseRepository.save(course);
    }

    public void approveReview (Long reviewId) {
        Review review = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        review.setReviewUploadStatus(UploadStatus.APPROVED);

        this.reviewRepository.save(review);
        this.courseService.updateCourseRating(review.getCourse().getCourseId());
    }

    public void denyReview (Long reviewId) {
        Review review = this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        this.reviewRepository.delete(review);

        review.setReviewUploadStatus(UploadStatus.DENIED);
        this.reviewRepository.save(review);
    }
}
