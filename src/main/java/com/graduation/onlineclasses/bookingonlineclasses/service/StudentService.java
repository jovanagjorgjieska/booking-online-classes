package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.RegisterRequest;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UserRole;
import com.graduation.onlineclasses.bookingonlineclasses.exception.StudentNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService implements UserService {

    private final BaseUserRepository baseUserRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public BaseUser getUserById(Long id) {
        return this.baseUserRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public BaseUser getUserByEmail(String email) {
        return this.baseUserRepository.findByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException(email));
    }

    @Override
    public BaseUser createUser(RegisterRequest user) {
        BaseUser newStudent = new BaseUser();
        newStudent.setEmail(user.getEmail());
        newStudent.setPassword(passwordEncoder.encode(user.getPassword()));
        newStudent.setFirstName(user.getFirstName());
        newStudent.setLastName(user.getLastName());
        newStudent.setUserRole(UserRole.valueOf(user.getRole()));

        return this.baseUserRepository.save(newStudent);
    }

    @Override
    public BaseUser updateUser(Long userId, BaseUser user) {
        Optional<BaseUser> student = this.baseUserRepository.findById(userId);

        if(student.isPresent()) {
            if(user.getEmail() != null)
                student.get().setEmail(user.getEmail());
            if(user.getPassword() != null)
                student.get().setPassword(passwordEncoder.encode(user.getPassword()));
            if(user.getFirstName() != null)
                student.get().setFirstName(user.getFirstName());
            if(user.getLastName() != null)
                student.get().setLastName(user.getLastName());

            return baseUserRepository.save(student.get());
        } else {
            throw new StudentNotFoundException(userId);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<BaseUser> studentToRemove = this.baseUserRepository.findById(userId);
        if(studentToRemove.isPresent()) {
            this.baseUserRepository.delete(studentToRemove.get());
        } else {
            throw new StudentNotFoundException(userId);
        }
    }
}
