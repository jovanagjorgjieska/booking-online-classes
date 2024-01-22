package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnrollmentDTO {

    private Long studentId;
    private Long teacherId;
    private Long courseId;
}
