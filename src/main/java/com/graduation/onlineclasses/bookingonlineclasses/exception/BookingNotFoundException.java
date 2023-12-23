package com.graduation.onlineclasses.bookingonlineclasses.exception;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(Long id) {
        super("Could not find booking " + id);
    }
}
