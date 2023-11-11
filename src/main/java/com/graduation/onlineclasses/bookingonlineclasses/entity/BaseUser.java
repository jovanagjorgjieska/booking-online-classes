package com.graduation.onlineclasses.bookingonlineclasses.entity;

import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class BaseUser {

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
