package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Getter;

@Getter
public class CourseDTO {

    private String courseName;
    private String description;
    private Integer availablePositions;
    private Double price;
}
