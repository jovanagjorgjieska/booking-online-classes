package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.ReviewDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Review;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UploadStatus;
import com.graduation.onlineclasses.bookingonlineclasses.exception.ReviewNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseService courseService;

    public List<Review> getAllReviews () {
        return this.reviewRepository.findAll();
    }

    public Review getReviewById (Long id) {
        return this.reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));
    }

    public List<Review> getAllReviewsByCourse (Long courseId) {
        Course course = this.courseService.getCourse(courseId);

        return this.reviewRepository.findAllByCourse(course);
    }

    //TODO: Add validation for the score range
    //TODO: Add validation if the student participated the course
    public Review createReview (ReviewDTO reviewDTO) {
        Course course = this.courseService.getCourse(reviewDTO.getCourseId());

        Review review = new Review();
        review.setCourse(course);
        review.setAuthor(reviewDTO.getAuthor());
        review.setDescription(reviewDTO.getDescription());
        review.setScore(reviewDTO.getScore());
        review.setReviewUploadStatus(UploadStatus.PENDING);

        return this.reviewRepository.save(review);
    }

    public Review editReview (Long reviewId, ReviewDTO reviewDTO) {
        Optional<Review> review = this.reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            if (reviewDTO.getDescription() != null)
                review.get().setDescription(reviewDTO.getDescription());
            if (reviewDTO.getScore() != null)
                review.get().setScore(reviewDTO.getScore());

            return this.reviewRepository.save(review.get());
        } else {
            throw new ReviewNotFoundException(reviewId);
        }
    }

    public void deleteReview (Long reviewId) {
        Optional<Review> review = this.reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            this.reviewRepository.delete(review.get());
        } else {
            throw new ReviewNotFoundException(reviewId);
        }
    }
}