package com.graduation.onlineclasses.bookingonlineclasses.repository;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Enrollment;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByStudent(BaseUser student);

    List<Enrollment> findAllByCourse(Course course);

    List<Enrollment> findAllByTeacher(BaseUser teacher);
}
