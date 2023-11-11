package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService implements UserService<Teacher> {

    private final TeacherRepository teacherRepository;

    @Override
    public Optional<Teacher> getUserByEmail(String email) {
        return this.teacherRepository.findByEmail(email);
    }

    @Override
    public Optional<Teacher> createUser(BaseUser user) {
        Teacher newTeacher = new Teacher();
        newTeacher.setEmail(user.getEmail());
        newTeacher.setPassword(user.getPassword());
        newTeacher.setUserRole(user.getUserRole());

        return Optional.of(this.teacherRepository.save(newTeacher));
    }

    @Override
    public Optional<Teacher> updateUser(Teacher user) {
        Teacher updatedTeacher = new Teacher();
        updatedTeacher.setEmail(user.getEmail());
        updatedTeacher.setPassword(user.getPassword());
        updatedTeacher.setEducation(user.getEducation());
        updatedTeacher.setOccupation(user.getOccupation());
        updatedTeacher.setCourses(user.getCourses());
        //TODO: Should we have this line?
        updatedTeacher.setReviews(user.getReviews());
        this.teacherRepository.save(updatedTeacher);

        return Optional.of(updatedTeacher);
    }

    @Override
    public Teacher deleteUser(Long userId) {
        Optional<Teacher> teacherToRemove = this.teacherRepository.findById(userId);

        return teacherToRemove.orElse(null);
    }
}
