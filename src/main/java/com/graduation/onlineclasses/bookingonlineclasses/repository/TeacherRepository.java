package com.graduation.onlineclasses.bookingonlineclasses.repository;

import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmail(String email);
}