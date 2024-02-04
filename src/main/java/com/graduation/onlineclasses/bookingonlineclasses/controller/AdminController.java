package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("courses/{courseId}/approve")
    public ResponseEntity<String> approveCourse (@PathVariable Long courseId) {
        this.adminService.approveCourse(courseId);
        return new ResponseEntity<>("Course approved successfully", HttpStatus.NO_CONTENT);
    }

    @PostMapping("courses/{courseId}/deny")
    public ResponseEntity<String> denyCourse (@PathVariable Long courseId) {
        this.adminService.denyCourse(courseId);
        return new ResponseEntity<>("Course denied successfully", HttpStatus.NO_CONTENT);
    }

    @PostMapping("reviews/{reviewId}/approve")
    public ResponseEntity<String> approveReview (@PathVariable Long reviewId) {
        this.adminService.approveReview(reviewId);
        return new ResponseEntity<>("Review approved successfully", HttpStatus.NO_CONTENT);
    }

    @PostMapping("reviews/{reviewId}/deny")
    public ResponseEntity<String> denyReview (@PathVariable Long reviewId) {
        this.adminService.denyReview(reviewId);
        return new ResponseEntity<>("Review denied successfully", HttpStatus.NO_CONTENT);
    }
}
