package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;

import java.util.Optional;

public interface UserService<T extends BaseUser> {

    Optional<T> getUserById(Long id);
    Optional<T> getUserByEmail (String email);

    T createUser (BaseUser user);

    Optional<T> updateUser (Long userId, T user);

    T deleteUser(Long userId);
}
