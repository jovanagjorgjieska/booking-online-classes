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
@RequiredArgsConstructor
public class TeacherService implements UserService {

    private final BaseUserRepository baseUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BaseUser getUserById(Long id) {
        return this.baseUserRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    @Override
    public BaseUser getUserByEmail(String email) {
        return this.baseUserRepository.findByEmail(email)
                .orElseThrow(() -> new TeacherNotFoundException(email));
    }

    @Override
    public BaseUser createUser(RegisterRequest user) {
        BaseUser newTeacher = new BaseUser();
        newTeacher.setEmail(user.getEmail());
        newTeacher.setPassword(passwordEncoder.encode(user.getPassword()));
        newTeacher.setUserRole(UserRole.valueOf(user.getRole()));

        return this.baseUserRepository.save(newTeacher);
    }

    @Override
    public BaseUser updateUser(Long userId, BaseUser user) {
        Optional<BaseUser> teacher = this.baseUserRepository.findById(userId);

        if(teacher.isPresent()) {
            if(user.getEmail() != null)
                teacher.get().setEmail(user.getEmail());
            if(user.getPassword() != null)
                teacher.get().setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getEducation() != null)
                teacher.get().setEducation(user.getEducation());
            if(user.getOccupation() != null)
                teacher.get().setOccupation(user.getOccupation());

            return this.baseUserRepository.save(teacher.get());
        } else {
            throw new TeacherNotFoundException(userId);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<BaseUser> teacherToRemove = this.baseUserRepository.findById(userId);
        if(teacherToRemove.isPresent()) {
            this.baseUserRepository.delete(teacherToRemove.get());
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
