package com.graduation.onlineclasses.bookingonlineclasses.repository;

import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.CourseCategory;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByCourseNameContainingIgnoreCase (String searchString);

    List<Course> findAllByCourseCategoryAndCourseType (CourseCategory category, CourseType type);

    List<Course> findAllByCourseCategory (CourseCategory category);

    List<Course> findAllByCourseType (CourseType type);

}
