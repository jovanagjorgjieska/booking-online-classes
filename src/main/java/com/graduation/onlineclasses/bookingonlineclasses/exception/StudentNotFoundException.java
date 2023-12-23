package com.graduation.onlineclasses.bookingonlineclasses.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Could not find student " + id);
    }

    public StudentNotFoundException(String email) {
        super("Could not find student " + email);
    }
}
