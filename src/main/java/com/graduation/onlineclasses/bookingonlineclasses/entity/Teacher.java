package com.graduation.onlineclasses.bookingonlineclasses.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Teacher extends BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teacherId;

    private String education;

    private String occupation;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Course> courses;

    @OneToMany
    private List<Review> reviews;
}
