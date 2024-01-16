package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.exception.StudentNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.BaseUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService extends UserService {

    public StudentService(BaseUserRepository baseUserRepository, PasswordEncoder passwordEncoder) {
        super(baseUserRepository, passwordEncoder);
    }


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
            if(user.getPhoneNumber() != null)
                student.get().setPhoneNumber(user.getPhoneNumber());

            return baseUserRepository.save(student.get());
        } else {
            throw new StudentNotFoundException(userId);
        }
    }
}
