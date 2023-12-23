package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDTO {

    public String email;
    public String password;
}
