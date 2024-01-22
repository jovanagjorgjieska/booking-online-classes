package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.EnrollmentDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Enrollment;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.exception.EnrollmentNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CourseService courseService;

    public Enrollment getEnrollmentById (Long id) {
        return this.enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException(id));
    }

    public List<Enrollment> getAllEnrollmentsByCourse (Long courseId) {
        Course course = this.courseService.getCourse(courseId);

        return this.enrollmentRepository.findAllByCourse(course);
    }

    public List<Enrollment> getAllEnrollmentsByTeacher (Long teacherId) {
        BaseUser teacher = this.teacherService.getUserById(teacherId);

        return this.enrollmentRepository.findAllByTeacher(teacher);
    }

    public List<Enrollment> getAllEnrollmentsByStudent (Long studentId) {
        BaseUser student = this.studentService.getUserById(studentId);

        return this.enrollmentRepository.findAllByStudent(student);
    }

    //TODO: add validation here
    @Transactional
    public Enrollment enrollToCourse (EnrollmentDTO enrollmentDTO) {
        Course course = this.courseService.getCourse(enrollmentDTO.getCourseId());
        BaseUser teacher = this.teacherService.getUserById(enrollmentDTO.getTeacherId());
        BaseUser student = this.studentService.getUserById(enrollmentDTO.getStudentId());

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setTeacher(teacher);
        enrollment.setStudent(student);
        enrollment.setEnrollmentDate(LocalDate.now());

        this.courseService.changeCourseAvailableAndBookedPositions(course.getCourseId(),
                course.getAvailablePositions() -1,
                course.getBookedPositions() + 1);

        return this.enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void cancelEnrollment (Long enrollmentId) {
        Optional<Enrollment> enrollment = this.enrollmentRepository.findById(enrollmentId);

        if (enrollment.isPresent()) {
            this.courseService.changeCourseAvailableAndBookedPositions(enrollment.get().getCourse().getCourseId(),
                    enrollment.get().getCourse().getAvailablePositions() + 1,
                    enrollment.get().getCourse().getBookedPositions() - 1);
            this.enrollmentRepository.delete(enrollment.get());
        } else {
            throw new EnrollmentNotFoundException(enrollmentId);
        }
    }
}
