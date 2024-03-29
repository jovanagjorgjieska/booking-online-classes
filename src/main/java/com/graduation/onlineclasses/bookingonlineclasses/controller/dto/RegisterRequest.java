package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Integer phoneNumber;
}
