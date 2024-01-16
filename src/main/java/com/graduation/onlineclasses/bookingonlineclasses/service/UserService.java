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
public class UserService {

    final BaseUserRepository baseUserRepository;
    final PasswordEncoder passwordEncoder;

    public BaseUser getUserById(Long id) {
        return this.baseUserRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public BaseUser getUserByEmail(String email) {
        return this.baseUserRepository.findByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException(email));
    }

    public BaseUser createUser(RegisterRequest user) {
        BaseUser newStudent = new BaseUser();
        newStudent.setEmail(user.getEmail());
        newStudent.setPassword(passwordEncoder.encode(user.getPassword()));
        newStudent.setFirstName(user.getFirstName());
        newStudent.setLastName(user.getLastName());
        newStudent.setUserRole(UserRole.valueOf(user.getRole()));
        newStudent.setPhoneNumber(user.getPhoneNumber());

        return this.baseUserRepository.save(newStudent);
    }

    public void deleteUser(Long userId) {
        Optional<BaseUser> studentToRemove = this.baseUserRepository.findById(userId);
        if(studentToRemove.isPresent()) {
            this.baseUserRepository.delete(studentToRemove.get());
        } else {
            throw new StudentNotFoundException(userId);
        }
    }
}
