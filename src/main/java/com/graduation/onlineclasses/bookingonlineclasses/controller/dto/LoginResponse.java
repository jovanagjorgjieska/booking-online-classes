package com.graduation.onlineclasses.bookingonlineclasses.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
}
