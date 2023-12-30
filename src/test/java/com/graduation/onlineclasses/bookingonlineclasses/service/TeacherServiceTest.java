package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.RegisterRequest;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UserRole;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.exception.TeacherNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.BaseUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @InjectMocks
    TeacherService classUnderTest;

    @Mock
    private BaseUserRepository baseUserRepository;

    private final Long TEST_ID = 101L;
    private final String TEST_EMAIL = "mock@mail.com";

    @Test
    public void getUserByIdTest() {
        BaseUser mockTeacher = createMockTeacher();

        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.of(mockTeacher));

        BaseUser result = classUnderTest.getUserById(TEST_ID);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_ID, result.getUserId())
        );
    }

    @Test
    public void getUserByIdTest_TeacherDoesNotExist() {

        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getUserById(TEST_ID));
    }

    @Test
    public void getUserByEmailTest() {
        BaseUser mockTeacher = createMockTeacher();
        mockTeacher.setEmail(TEST_EMAIL);

        when(baseUserRepository.findByEmail(anyString())).thenReturn(Optional.of(mockTeacher));

        BaseUser result = classUnderTest.getUserByEmail(TEST_EMAIL);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_EMAIL, result.getEmail())
        );
    }

    @Test
    public void getUserByEmail_TeacherDoesNotExist() {
        when(baseUserRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getUserByEmail(TEST_EMAIL));
    }

    @Test
    public void createUserTest() {

        RegisterRequest mockUser = new RegisterRequest();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setPassword("testpass");
        mockUser.setRole("TEACHER");

        BaseUser teacher = createMockTeacher();
        teacher.setEmail(TEST_EMAIL);
        teacher.setPassword("testpass");
        teacher.setUserRole(UserRole.TEACHER);

        when(baseUserRepository.save(any(BaseUser.class))).thenReturn(teacher);

        BaseUser result = classUnderTest.createUser(mockUser);

        verify(baseUserRepository, times(1)).save(any(BaseUser.class));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_EMAIL, result.getEmail()),
                () -> assertEquals(mockUser.getPassword(), result.getPassword()),
                () -> assertEquals(mockUser.getRole(), result.getUserRole().toString())
        );
    }

    @Test
    public void updateUser() {
        BaseUser teacher = createMockTeacher();
        teacher.setEmail("test@mail.com");
        teacher.setPassword("testpass");
        teacher.setEducation("High School");
        teacher.setOccupation("Student");

        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(baseUserRepository.save(any(BaseUser.class))).thenReturn(teacher);

        BaseUser result = classUnderTest.updateUser(TEST_ID, teacher);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(teacher.getEmail(), result.getEmail()),
                () -> assertEquals(teacher.getPassword(), result.getPassword()),
                () -> assertEquals(teacher.getEducation(), result.getEducation()),
                () -> assertEquals(teacher.getOccupation(), result.getOccupation())
        );
    }

    @Test
    public void updateUser_TeacherDoesNotExist() {
        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.updateUser(TEST_ID, createMockTeacher()));
    }

    @Test
    public void deleteUserTest() {
        BaseUser teacher = createMockTeacher();

        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        doNothing().when(baseUserRepository).delete(any(BaseUser.class));

        classUnderTest.deleteUser(TEST_ID);

        verify(baseUserRepository, times(1)).delete(any(BaseUser.class));
    }

    @Test
    public void deleteUserTest_TeacherDoesNotExist() {
        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.deleteUser(TEST_ID));
    }

    @Test
    public void getAllTeachersTest() {
        BaseUser teacher1 = createMockTeacher();
        BaseUser teacher2 = createMockTeacher();
        List<BaseUser> teacherList = List.of(teacher1, teacher2);

        when(baseUserRepository.findAllByUserRole(any(UserRole.class))).thenReturn(teacherList);

        List<BaseUser> result = classUnderTest.getAllTeachers("TEACHER");

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size())
        );
    }

    @Test
    public void getAllCoursesForTeacherTest() {
        Course course1 = createMockCourse();
        Course course2 = createMockCourse();

        BaseUser teacher = createMockTeacher();
        teacher.setCourses(List.of(course1, course2));

        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        List<Course> result = classUnderTest.getAllCoursesForTeacher(TEST_ID);

        assertEquals(2, result.size());
    }

    @Test
    public void getAllCoursesForTeacherTest_teacherDoesNotExist() {
        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getAllCoursesForTeacher(TEST_ID));
    }

    @Test
    public void getTeachersCourseTest() {
        BaseUser teacher = createMockTeacher();
        Course course1 = createMockCourse();
        Course course2 = createMockCourse();
        course2.setCourseId(102L);

        teacher.setCourses(List.of(course1, course2));

        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        Course result = classUnderTest.getTeachersCourse(TEST_ID, TEST_ID);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_ID, result.getCourseId())
        );
    }

    @Test
    public void getTeachersCourseTest_teacherDoesNotExist() {
        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getTeachersCourse(TEST_ID, TEST_ID));
    }

    @Test
    public void getTeachersCourseTest_courseDoesNotExist() {
        BaseUser teacher = createMockTeacher();
        Course course = createMockCourse();
        teacher.setCourses(List.of(course));

        when(baseUserRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        assertThrows(CourseNotFoundException.class,
                () -> classUnderTest.getTeachersCourse(TEST_ID, 102L));
    }

    private BaseUser createMockTeacher() {
        BaseUser teacher = new BaseUser();
        teacher.setUserId(TEST_ID);

        return teacher;
    }

    private Course createMockCourse() {
        Course course = new Course();
        course.setCourseId(TEST_ID);

        return course;
    }

}
