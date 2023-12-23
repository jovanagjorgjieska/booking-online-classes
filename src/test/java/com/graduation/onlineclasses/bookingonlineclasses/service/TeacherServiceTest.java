package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Teacher;
import com.graduation.onlineclasses.bookingonlineclasses.entity.enums.UserRole;
import com.graduation.onlineclasses.bookingonlineclasses.exception.CourseNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.exception.TeacherNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.TeacherRepository;
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
    private TeacherRepository teacherRepository;

    private final Long TEST_ID = 101L;
    private final String TEST_EMAIL = "mock@mail.com";

    @Test
    public void getUserByIdTest() {
        Teacher mockTeacher = createMockTeacher();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(mockTeacher));

        Teacher result = classUnderTest.getUserById(TEST_ID);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_ID, result.getTeacherId())
        );
    }

    @Test
    public void getUserByIdTest_TeacherDoesNotExist() {

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getUserById(TEST_ID));
    }

    @Test
    public void getUserByEmailTest() {
        Teacher mockTeacher = createMockTeacher();
        mockTeacher.setEmail(TEST_EMAIL);

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.of(mockTeacher));

        Teacher result = classUnderTest.getUserByEmail(TEST_EMAIL);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_EMAIL, result.getEmail())
        );
    }

    @Test
    public void getUserByEmail_TeacherDoesNotExist() {
        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getUserByEmail(TEST_EMAIL));
    }

    @Test
    public void createUserTest() {

        BaseUser mockUser = new BaseUser();
        mockUser.setEmail(TEST_EMAIL);
        mockUser.setPassword("testpass");
        mockUser.setUserRole(UserRole.TEACHER);

        Teacher teacher = createMockTeacher();
        teacher.setEmail(TEST_EMAIL);
        teacher.setPassword("testpass");
        teacher.setUserRole(UserRole.TEACHER);

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Teacher result = classUnderTest.createUser(mockUser);

        verify(teacherRepository, times(1)).save(any(Teacher.class));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_EMAIL, result.getEmail()),
                () -> assertEquals(mockUser.getPassword(), result.getPassword()),
                () -> assertEquals(mockUser.getUserRole(), result.getUserRole())
        );
    }

    @Test
    public void updateUser() {
        Teacher teacher = createMockTeacher();
        teacher.setEmail("test@mail.com");
        teacher.setPassword("testpass");
        teacher.setEducation("High School");
        teacher.setOccupation("Student");

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Teacher result = classUnderTest.updateUser(TEST_ID, teacher);

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
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.updateUser(TEST_ID, createMockTeacher()));
    }

    @Test
    public void deleteUserTest() {
        Teacher teacher = createMockTeacher();

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        doNothing().when(teacherRepository).delete(any(Teacher.class));

        classUnderTest.deleteUser(TEST_ID);

        verify(teacherRepository, times(1)).delete(any(Teacher.class));
    }

    @Test
    public void deleteUserTest_TeacherDoesNotExist() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.deleteUser(TEST_ID));
    }

    @Test
    public void getAllTeachersTest() {
        Teacher teacher1 = createMockTeacher();
        Teacher teacher2 = createMockTeacher();
        List<Teacher> teacherList = List.of(teacher1, teacher2);

        when(teacherRepository.findAll()).thenReturn(teacherList);

        List<Teacher> result = classUnderTest.getAllTeachers();

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(2, result.size())
        );
    }

    @Test
    public void getAllCoursesForTeacherTest() {
        Course course1 = createMockCourse();
        Course course2 = createMockCourse();

        Teacher teacher = createMockTeacher();
        teacher.setCourses(List.of(course1, course2));

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        List<Course> result = classUnderTest.getAllCoursesForTeacher(TEST_ID);

        assertEquals(2, result.size());
    }

    @Test
    public void getAllCoursesForTeacherTest_teacherDoesNotExist() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getAllCoursesForTeacher(TEST_ID));
    }

    @Test
    public void getTeachersCourseTest() {
        Teacher teacher = createMockTeacher();
        Course course1 = createMockCourse();
        Course course2 = createMockCourse();
        course2.setCourseId(102L);

        teacher.setCourses(List.of(course1, course2));

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        Course result = classUnderTest.getTeachersCourse(TEST_ID, TEST_ID);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(TEST_ID, result.getCourseId())
        );
    }

    @Test
    public void getTeachersCourseTest_teacherDoesNotExist() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TeacherNotFoundException.class,
                () -> classUnderTest.getTeachersCourse(TEST_ID, TEST_ID));
    }

    @Test
    public void getTeachersCourseTest_courseDoesNotExist() {
        Teacher teacher = createMockTeacher();
        Course course = createMockCourse();
        teacher.setCourses(List.of(course));

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        assertThrows(CourseNotFoundException.class,
                () -> classUnderTest.getTeachersCourse(TEST_ID, 102L));
    }

    private Teacher createMockTeacher() {
        Teacher teacher = new Teacher();
        teacher.setTeacherId(TEST_ID);

        return teacher;
    }

    private Course createMockCourse() {
        Course course = new Course();
        course.setCourseId(TEST_ID);

        return course;
    }

}
