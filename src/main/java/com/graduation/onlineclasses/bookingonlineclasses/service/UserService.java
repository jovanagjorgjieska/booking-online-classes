package com.graduation.onlineclasses.bookingonlineclasses.service;

import com.graduation.onlineclasses.bookingonlineclasses.controller.dto.RegisterRequest;
import com.graduation.onlineclasses.bookingonlineclasses.entity.BaseUser;

public interface UserService {

    BaseUser getUserById(Long id);
    BaseUser getUserByEmail (String email);

    BaseUser createUser (RegisterRequest user);

    BaseUser updateUser (Long userId, BaseUser user);

    void deleteUser(Long userId);
}
