package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Student;
import com.graduation.onlineclasses.bookingonlineclasses.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService implements UserService<Student> {

    private final StudentRepository studentRepository;


    @Override
    public Optional<Student> getUserByEmail(String email) {
        return this.studentRepository.findByEmail(email);
    }

    @Override
    public Optional<Student> createUser(BaseUser user) {
        Student newStudent = new Student();
        newStudent.setEmail(user.getEmail());
        newStudent.setPassword(user.getPassword());
        newStudent.setUserRole(user.getUserRole());

        return Optional.of(this.studentRepository.save(newStudent));
    }

    @Override
    public Optional<Student> updateUser(Student user) {
        Student updatedStudent = new Student();
        updatedStudent.setEmail(user.getEmail());
        updatedStudent.setPassword(user.getPassword());
        this.studentRepository.save(updatedStudent);

        return Optional.of(updatedStudent);
    }

    @Override
    public Student deleteUser(Long userId) {
        Optional<Student> studentToRemove = this.studentRepository.findById(userId);
        return studentToRemove.orElse(null);
    }
}
