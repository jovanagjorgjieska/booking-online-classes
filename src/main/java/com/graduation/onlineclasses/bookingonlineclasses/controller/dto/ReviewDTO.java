package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {

    private String author;
    private Long courseId;
    private Integer score;
    private String description;
}