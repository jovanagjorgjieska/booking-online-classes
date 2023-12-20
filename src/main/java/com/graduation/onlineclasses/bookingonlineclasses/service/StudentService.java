package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Student;
import com.graduation.onlineclasses.bookingonlineclasses.exception.TeacherNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService implements UserService<Student> {

    private final StudentRepository studentRepository;


    @Override
    public Optional<Student> getUserById(Long id) {
        return this.studentRepository.findById(id);
    }

    @Override
    public Optional<Student> getUserByEmail(String email) {
        return this.studentRepository.findByEmail(email);
    }

    @Override
    public Student createUser(BaseUser user) {
        Student newStudent = new Student();
        newStudent.setEmail(user.getEmail());
        newStudent.setPassword(user.getPassword());
        newStudent.setUserRole(user.getUserRole());

        this.studentRepository.save(newStudent);

        return newStudent;
    }

    @Override
    public Student updateUser(Long userId, Student user) {
        Optional<Student> student = studentRepository.findById(userId);

        if(student.isPresent()) {
            student.get().setEmail(user.getEmail());
            student.get().setPassword(user.getPassword());

            return studentRepository.save(student.get());
        } else {
            //TODO: Make new StudentNotFoundException !
            throw new TeacherNotFoundException(userId);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<Student> studentToRemove = this.studentRepository.findById(userId);
    }
}
