package com.graduation.onlineclasses.bookingonlineclasses.repository;

import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByTeacher(Teacher teacher);
}
