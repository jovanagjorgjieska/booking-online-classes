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
        teacher.setEducation(teacherDTO.getEducation());
        teacher.setOccupation(teacherDTO.getOccupation());

        return teacher;
    }

    public BaseUser mapFromStudentDtoToUser(StudentDTO studentDTO) {
        BaseUser student = new BaseUser();
        student.setEmail(studentDTO.getEmail());
        student.setPassword(studentDTO.getPassword());

        return student;
    }

}
