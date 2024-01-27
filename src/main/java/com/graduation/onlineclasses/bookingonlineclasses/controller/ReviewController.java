package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.ReviewDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Review;
import com.graduation.onlineclasses.bookingonlineclasses.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview (@PathVariable Long reviewId) {
        return ResponseEntity.ok(this.reviewService.getReviewById(reviewId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Review>> getReviewsByCourse (@PathVariable Long courseId) {
        return ResponseEntity.ok(this.reviewService.getAllReviewsByCourse(courseId));
    }

    @PostMapping
    public ResponseEntity<Review> addReview (@RequestBody ReviewDTO reviewDTO) {
        Review review = this.reviewService.createReview(reviewDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(review.getReviewId())
                .toUri();
        return ResponseEntity.created(location).body(review);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> editReview (@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        Review review = this.reviewService.editReview(reviewId, reviewDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(review.getReviewId())
                .toUri();
        return ResponseEntity.created(location).body(review);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview (@PathVariable Long reviewId) {
        this.reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.NO_CONTENT);
    }
}
