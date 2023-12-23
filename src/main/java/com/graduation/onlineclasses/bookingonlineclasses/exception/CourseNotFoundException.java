package com.graduation.onlineclasses.bookingonlineclasses.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Could not find course " + id);
    }
}
