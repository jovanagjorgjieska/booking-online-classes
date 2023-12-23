package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
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
    public Teacher getUserById(Long id) {
        return this.teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
    }

    @Override
    public Teacher getUserByEmail(String email) {
        return this.teacherRepository.findByEmail(email)
                .orElseThrow(() -> new TeacherNotFoundException(email));
    }

    @Override
    public Teacher createUser(BaseUser user) {
        Teacher newTeacher = new Teacher();
        newTeacher.setEmail(user.getEmail());
        newTeacher.setPassword(user.getPassword());
        newTeacher.setUserRole(user.getUserRole());

        return this.teacherRepository.save(newTeacher);
    }

    @Override
    public Teacher updateUser(Long userId, Teacher user) {
        Optional<Teacher> teacher = this.teacherRepository.findById(userId);

        if(teacher.isPresent()) {
            if(user.getEmail() != null)
                teacher.get().setEmail(user.getEmail());
            if(user.getPassword() != null)
                teacher.get().setPassword(user.getPassword());
            if(user.getEducation() != null)
                teacher.get().setEducation(user.getEducation());
            if(user.getOccupation() != null)
                teacher.get().setOccupation(user.getOccupation());

            return this.teacherRepository.save(teacher.get());
        } else {
            throw new TeacherNotFoundException(userId);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<Teacher> teacherToRemove = this.teacherRepository.findById(userId);
        if(teacherToRemove.isPresent()) {
            this.teacherRepository.delete(teacherToRemove.get());
        } else {
            throw new TeacherNotFoundException(userId);
        }
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

    public Course getTeachersCourse(Long teacherId, Long courseId) {
        Optional<Teacher> teacher = this.teacherRepository.findById(teacherId);

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
