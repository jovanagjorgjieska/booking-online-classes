package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseId;

    private Long teacherId;

    private String courseName;

    private String description;

    private Integer bookedPositions;

    private Integer availablePositions;

    private Double price;
}
