package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDTO {

    private String email;
    private String password;
    public String firstName;
    public String lastName;
    private String education;
    private String occupation;
    private Integer phoneNumber;
}
