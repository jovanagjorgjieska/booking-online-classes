package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void createUser(BaseUser user) {
        Teacher newTeacher = new Teacher();
        newTeacher.setEmail(user.getEmail());
        newTeacher.setPassword(user.getPassword());
        newTeacher.setUserRole(user.getUserRole());

        this.teacherRepository.save(newTeacher);
    }

    @Override
    public Optional<Teacher> updateUser(Long userId, Teacher user) {
        Optional<Teacher> teacher = this.teacherRepository.findById(userId);

        if(teacher.isPresent()) {
            teacher.get().setEmail(user.getEmail());
            teacher.get().setPassword(user.getPassword());
            teacher.get().setEducation(user.getEducation());
            teacher.get().setOccupation(user.getOccupation());
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
}
