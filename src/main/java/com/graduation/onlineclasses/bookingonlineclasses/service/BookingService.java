package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.BookingDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Booking;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Course;
import com.graduation.onlineclasses.bookingonlineclasses.exception.BookingNotFoundException;
import com.graduation.onlineclasses.bookingonlineclasses.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final CourseService courseService;

    public Booking getBookingById (Long id) {
        return this.bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    public List<Booking> getAllBookingsByCourse (Long courseId) {
        Course course = this.courseService.getCourse(courseId);

        return this.bookingRepository.findAllByCourse(course);
    }

    public List<Booking> getAllBookingsByTeacher (Long teacherId) {
        BaseUser teacher = this.teacherService.getUserById(teacherId);

        return this.bookingRepository.findAllByTeacher(teacher);
    }

    public List<Booking> getAllBookingsByStudent (Long studentId) {
        BaseUser student = this.studentService.getUserById(studentId);

        return this.bookingRepository.findAllByStudent(student);
    }

    //TODO: add validation here
    @Transactional
    public Booking bookCourse (BookingDTO bookingDTO) {
        Course course = this.courseService.getCourse(bookingDTO.getCourseId());
        BaseUser teacher = this.teacherService.getUserById(bookingDTO.getTeacherId());
        BaseUser student = this.studentService.getUserById(bookingDTO.getStudentId());

        Booking booking = new Booking();
        booking.setCourse(course);
        booking.setTeacher(teacher);
        booking.setStudent(student);
        booking.setBookingDate(LocalDate.now());

        this.courseService.changeCourseAvailableAndBookedPositions(course.getCourseId(),
                course.getAvailablePositions() -1,
                course.getBookedPositions() + 1);

        return this.bookingRepository.save(booking);
    }

    @Transactional
    public void cancelBooking (Long bookingId) {
        Optional<Booking> booking = this.bookingRepository.findById(bookingId);

        if (booking.isPresent()) {
            this.courseService.changeCourseAvailableAndBookedPositions(booking.get().getCourse().getCourseId(),
                    booking.get().getCourse().getAvailablePositions() + 1,
                    booking.get().getCourse().getBookedPositions() - 1);
            this.bookingRepository.delete(booking.get());
        } else {
            throw new BookingNotFoundException(bookingId);
        }
    }
}
