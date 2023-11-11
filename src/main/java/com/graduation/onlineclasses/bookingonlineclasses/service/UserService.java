package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;

import java.util.Optional;

public interface UserService<T extends BaseUser> {

    Optional<T> getUserByEmail (String email);

    Optional<T> createUser (BaseUser user);

    Optional<T> updateUser (T user);

    T deleteUser(Long userId);
}
