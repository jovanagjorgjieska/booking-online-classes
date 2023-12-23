package com.graduation.onlineclasses.bookingonlineclasses.exception;

public class TeacherNotFoundException extends RuntimeException {

    public TeacherNotFoundException(Long id) {
        super("Could not find teacher " + id);
    }

    public TeacherNotFoundException(String email) {
        super("Could not find teacher " + email);
    }
}
