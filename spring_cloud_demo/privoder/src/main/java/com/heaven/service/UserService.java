package com.heaven.service;

import com.heaven.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Integer id);
}
