package com.example.doctorappdemo.service;

import com.example.doctorappdemo.entity.User;


public interface UserService {

    User findUserByEmail(String email);

    User findUserByUsername(String username);
    void save(User user);

    void saveNoPassword(User user);

    User findByEmail(String email);

    int findId(String username);
    User findUserById(int id);
}
