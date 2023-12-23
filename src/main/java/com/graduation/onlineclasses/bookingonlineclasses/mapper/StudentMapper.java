package com.graduation.onlineclasses.bookingonlineclasses.mapper;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.StudentDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public Student mapFromStudentDtoToStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());

        return student;
    }
}
