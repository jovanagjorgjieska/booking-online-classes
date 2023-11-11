package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Review {

    @Id
    private Long reviewId;

    private Long studentId;

    private Long teacherId;

    private Long courseId;

    private Integer score;

    private String description;
}
