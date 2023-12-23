package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;

public interface UserService<T extends BaseUser> {

    T getUserById(Long id);
    T getUserByEmail (String email);

    T createUser (BaseUser user);

    T updateUser (Long userId, T user);

    void deleteUser(Long userId);
}
