package com.graduation.onlineclasses.bookingonlineclasses.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(Long id) {
        super("Could not find review " + id);
    }
}