package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    private Long courseId;

    private Long teacherId;

    private String courseName;

    private String description;

    private Integer bookedPositions;

    private Integer availablePositions;

    private Double price;
}
