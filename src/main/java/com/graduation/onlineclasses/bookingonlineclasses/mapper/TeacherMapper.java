package com.graduation.onlineclasses.bookingonlineclasses.mapper;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.TeacherDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    public Teacher mapFromTeacherDtoToTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = new Teacher();
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setEducation(teacherDTO.getEducation());
        teacher.setOccupation(teacherDTO.getOccupation());

        return teacher;
    }

}
