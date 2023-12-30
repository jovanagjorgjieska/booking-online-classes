package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    private BaseUser student;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    private Integer score;

    private String description;
}
