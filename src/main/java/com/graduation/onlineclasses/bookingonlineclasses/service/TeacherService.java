package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.RegisterRequest;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UserRole;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.exception.TeacherNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherService extends UserService {

    public TeacherService(BaseUserRepository baseUserRepository, PasswordEncoder passwordEncoder) {
        super(baseUserRepository, passwordEncoder);
    }

    public BaseUser updateUser(Long userId, BaseUser user) {
        Optional<BaseUser> teacher = this.baseUserRepository.findById(userId);

        if(teacher.isPresent()) {
            if(user.getEmail() != null)
                teacher.get().setEmail(user.getEmail());
            if(user.getPassword() != null)
                teacher.get().setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getFirstName() != null)
                teacher.get().setFirstName(user.getFirstName());
            if(user.getLastName() != null)
                teacher.get().setLastName(user.getLastName());
            if(user.getEducation() != null)
                teacher.get().setEducation(user.getEducation());
            if(user.getOccupation() != null)
                teacher.get().setOccupation(user.getOccupation());
            if(user.getPhoneNumber() != null)
                teacher.get().setPhoneNumber(user.getPhoneNumber());

            return this.baseUserRepository.save(teacher.get());
        } else {
            throw new TeacherNotFoundException(userId);
        }
    }

    public List<BaseUser> getAllTeachers(String userRole) {
        return this.baseUserRepository.findAllByUserRole(UserRole.valueOf(userRole));
    }

    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        Optional<BaseUser> teacher = this.baseUserRepository.findById(teacherId);
        if(teacher.isPresent()) {
            return teacher.get().getCourses();
        } else {
            throw new TeacherNotFoundException(teacherId);
        }
    }

    public Course getTeachersCourse(Long teacherId, Long courseId) {
        Optional<BaseUser> teacher = this.baseUserRepository.findById(teacherId);

        if(teacher.isPresent()) {
            List<Course> courses = teacher.get().getCourses();
            for(Course course: courses) {
                if(Objects.equals(course.getCourseId(), courseId)) {
                    return course;
                }
            }
        } else {
            throw new TeacherNotFoundException(teacherId);
        }
        throw new CourseNotFoundException(courseId);
    }
}
