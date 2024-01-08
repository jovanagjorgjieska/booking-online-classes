package com.graduation.onlineclasses.bookingonlineclasses.mapper;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.StudentDTO;
import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.TeacherDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public BaseUser mapFromTeacherDtoToUser(TeacherDTO teacherDTO) {
        BaseUser teacher = new BaseUser();
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setEducation(teacherDTO.getEducation());
        teacher.setOccupation(teacherDTO.getOccupation());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());

        return teacher;
    }

    public BaseUser mapFromStudentDtoToUser(StudentDTO studentDTO) {
        BaseUser student = new BaseUser();
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setPhoneNumber(studentDTO.getPhoneNumber());

        return student;
    }

}
