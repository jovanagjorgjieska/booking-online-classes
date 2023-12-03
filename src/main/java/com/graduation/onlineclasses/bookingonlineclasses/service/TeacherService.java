package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.exception.TeacherNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService implements UserService<Teacher> {

    private final TeacherRepository teacherRepository;

    @Override
    public Optional<Teacher> getUserById(Long id) {
        return this.teacherRepository.findById(id);
    }

    @Override
    public Optional<Teacher> getUserByEmail(String email) {
        return this.teacherRepository.findByEmail(email);
    }

    @Override
    public Teacher createUser(BaseUser user) {
        Teacher newTeacher = new Teacher();
        newTeacher.setEmail(user.getEmail());
        newTeacher.setPassword(user.getPassword());
        newTeacher.setUserRole(user.getUserRole());

        this.teacherRepository.save(newTeacher);

        return newTeacher;
    }

    @Override
    public Optional<Teacher> updateUser(Long userId, Teacher user) {
        Optional<Teacher> teacher = this.teacherRepository.findById(userId);

        if(teacher.isPresent()) {
            if(user.getEmail() != null) {
                teacher.get().setEmail(user.getEmail());
            }
            if(user.getPassword() != null) {
                teacher.get().setPassword(user.getPassword());
            }
            if(user.getEducation() != null) {
                teacher.get().setEducation(user.getEducation());
            }
            if(user.getOccupation() != null) {
                teacher.get().setOccupation(user.getOccupation());
            }

            this.teacherRepository.save(teacher.get());
        }

        return teacher;
    }

    @Override
    public Teacher deleteUser(Long userId) {
        Optional<Teacher> teacherToRemove = this.teacherRepository.findById(userId);

        return teacherToRemove.orElse(null);
    }

    public List<Teacher> getAllTeachers() {
        return this.teacherRepository.findAll();
    }

    public List<Course> getAllCoursesForTeacher(Long teacherId) {
        Optional<Teacher> teacher = this.teacherRepository.findById(teacherId);
        if(teacher.isPresent()) {
            return teacher.get().getCourses();
        } else {
            throw new TeacherNotFoundException(teacherId);
        }
    }

    public Optional<Course> getTeachersCourse(Long teacherId, Long courseId) {
        Optional<Teacher> teacher = this.teacherRepository.findById(teacherId);

        if(teacher.isPresent()) {
            List<Course> courses = teacher.get().getCourses();
            for(Course c: courses) {
                if(Objects.equals(c.getCourseId(), courseId)) {
                    return Optional.of(c);
                }
            }
        } else {
            throw new TeacherNotFoundException(teacherId);
        }
        return Optional.empty();
    }
}
