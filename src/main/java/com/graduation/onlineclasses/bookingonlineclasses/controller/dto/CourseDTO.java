package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDTO {

    private String courseName;
    private String description;
    private Integer availablePositions;
    private String courseType;
    private String courseCategory;
    private Integer price;

}
