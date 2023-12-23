package com.graduation.onlineclasses.bookingonlineclasses.controller;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.BookingDTO;
import com.graduation.onlineclasses.bookingonlineclasses.entity.Booking;
import com.graduation.onlineclasses.bookingonlineclasses.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBooking (@PathVariable Long bookingId) {
        return ResponseEntity.ok(this.bookingService.getBookingById(bookingId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Booking>> getBookingsForCourse (@PathVariable Long courseId) {
        return ResponseEntity.ok(this.bookingService.getAllBookingsByCourse(courseId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Booking>> getBookingsForStudent (@PathVariable Long studentId) {
        return ResponseEntity.ok(this.bookingService.getAllBookingsByStudent(studentId));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Booking>> getBookingsForTeacher (@PathVariable Long teacherId) {
        return ResponseEntity.ok(this.bookingService.getAllBookingsByTeacher(teacherId));
    }

    @PostMapping
    public ResponseEntity<Booking> bookCourse (@RequestBody BookingDTO bookingDTO) {
        Booking booking = this.bookingService.bookCourse(bookingDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(booking.getBookingId())
                .toUri();
        return ResponseEntity.created(location).body(booking);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking (@PathVariable Long bookingId) {
        this.bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>("Booking cancelled successfully", HttpStatus.NO_CONTENT);
    }
}
