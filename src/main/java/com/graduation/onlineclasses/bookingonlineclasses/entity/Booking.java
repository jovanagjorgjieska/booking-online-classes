package com.graduation.onlineclasses.bookingonlineclasses.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    private BaseUser student;

    @ManyToOne
    @JoinColumn(name = "teacherId", nullable = false)
    private BaseUser teacher;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate bookingDate;
}
