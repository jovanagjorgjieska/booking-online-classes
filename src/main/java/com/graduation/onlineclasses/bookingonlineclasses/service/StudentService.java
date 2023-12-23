package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Student;
import com.graduation.onlineclasses.bookingonlineclasses.exception.StudentNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService implements UserService<Student> {

    private final StudentRepository studentRepository;


    @Override
    public Student getUserById(Long id) {
        return this.studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public Student getUserByEmail(String email) {
        return this.studentRepository.findByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException(email));
    }

    @Override
    public Student createUser(BaseUser user) {
        Student newStudent = new Student();
        newStudent.setEmail(user.getEmail());
        newStudent.setPassword(user.getPassword());
        newStudent.setUserRole(user.getUserRole());

        return this.studentRepository.save(newStudent);
    }

    @Override
    public Student updateUser(Long userId, Student user) {
        Optional<Student> student = studentRepository.findById(userId);

        if(student.isPresent()) {
            if(user.getEmail() != null)
                student.get().setEmail(user.getEmail());
            if(user.getPassword() != null)
                student.get().setPassword(user.getPassword());

            return studentRepository.save(student.get());
        } else {
            throw new StudentNotFoundException(userId);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<Student> studentToRemove = this.studentRepository.findById(userId);
        if(studentToRemove.isPresent()) {
            this.studentRepository.delete(studentToRemove.get());
        } else {
            throw new StudentNotFoundException(userId);
        }
    }
}
