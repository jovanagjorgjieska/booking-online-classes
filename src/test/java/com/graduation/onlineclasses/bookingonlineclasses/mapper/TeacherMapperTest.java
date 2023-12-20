package com.graduation.onlineclasses.bookingonlineclasses.mapper;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.TeacherDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TeacherMapperTest {

    @InjectMocks
    TeacherMapper classUnderTest;

    @Test
    public void testMapFromTeacherDtoToTeacher() {

        TeacherDTO mockTeacherDto = createMockTeacherDto();

        Teacher result = classUnderTest.mapFromTeacherDtoToTeacher(mockTeacherDto);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(mockTeacherDto.getEmail(), result.getEmail()),
                () -> assertEquals(mockTeacherDto.getPassword(), result.getPassword()),
                () -> assertEquals(mockTeacherDto.getEducation(), result.getEducation()),
                () -> assertEquals(mockTeacherDto.getOccupation(), result.getOccupation())
        );
    }

    private TeacherDTO createMockTeacherDto() {

        return TeacherDTO.builder()
                .email("mock@email.com")
                .password("mockpass")
                .education("High School")
                .occupation("Student")
                .build();
    }
}
