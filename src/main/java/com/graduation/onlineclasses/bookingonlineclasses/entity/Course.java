package com.graduation.onlineclasses.bookingonlineclasses.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.CourseCategory;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.CourseType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "teacherId", nullable = false)
    private BaseUser teacher;

    private String courseName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String details;

    private Integer bookedPositions;

    private Integer availablePositions;

    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @Enumerated(EnumType.STRING)
    private CourseCategory courseCategory;

    private Double price;

    private Float rating;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", teacher=" + (teacher != null ? teacher.getUserId() : null) +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", details='" + details + '\'' +
                ", bookedPositions=" + bookedPositions +
                ", availablePositions=" + availablePositions +
                ", courseType=" + courseType +
                ", courseCategory=" + courseCategory +
                ", price=" + price +
                '}';
    }
}
