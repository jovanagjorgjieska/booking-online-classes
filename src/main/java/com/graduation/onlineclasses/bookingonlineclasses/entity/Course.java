package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "teacherId", nullable = false)
    private Teacher teacher;

    private String courseName;

    private String description;

    private Integer bookedPositions;

    private Integer availablePositions;

    private Double price;
}
