package com.graduation.onlineclasses.bookingonlineclasses.repository;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Booking;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByStudent(BaseUser student);

    List<Booking> findAllByCourse(Course course);

    List<Booking> findAllByTeacher(BaseUser teacher);
}
