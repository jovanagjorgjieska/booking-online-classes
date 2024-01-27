package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewId;

    private String author;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    private Integer score;

    private String description;
}
