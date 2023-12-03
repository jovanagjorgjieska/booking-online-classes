package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;
}
