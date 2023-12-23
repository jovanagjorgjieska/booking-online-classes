package com.graduation.onlineclasses.bookingonlineclasses.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
