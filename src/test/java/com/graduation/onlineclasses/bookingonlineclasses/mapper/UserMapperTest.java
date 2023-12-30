package com.graduation.onlineclasses.bookingonlineclasses.mapper;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.TeacherDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    UserMapper classUnderTest;

    @Test
    public void testMapFromTeacherDtoToTeacher() {

        TeacherDTO mockTeacherDto = createMockTeacherDto();

        BaseUser result = classUnderTest.mapFromTeacherDtoToUser(mockTeacherDto);

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
