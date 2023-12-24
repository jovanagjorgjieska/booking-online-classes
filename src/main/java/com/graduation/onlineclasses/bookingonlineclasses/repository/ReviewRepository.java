package com.graduation.onlineclasses.bookingonlineclasses.repository;

import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Review;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByCourse(Course course);

    List<Review> findAllByStudent(Student student);
}
