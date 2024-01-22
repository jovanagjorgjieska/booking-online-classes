package com.graduation.onlineclasses.bookingonlineclasses.exception;

public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(Long id) {
        super("Could not find enrollment " + id);
    }
}
