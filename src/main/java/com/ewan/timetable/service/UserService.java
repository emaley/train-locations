package com.ewan.timetable.service;

import com.ewan.timetable.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
