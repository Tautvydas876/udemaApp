package com.example.doctorappdemo.service;

import com.example.doctorappdemo.dao.UserRepository;
import com.example.doctorappdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUserName(username);
    }

    @Override
    public void save(User user) {
        if (!user.getPassword().equals("")) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            User userTempID = userRepository.findById(user.getId());
            String userTempPassword = userTempID.getPassword();
            user.setPassword(userTempPassword);
        }
        userRepository.save(user);
    }

    @Override
    public void saveNoPassword(User user) {
//        System.out.println("user oldEmail from db => " + user.getOldEmail());
//        System.out.println("user Email from db => " + user.getEmail());

        User userTempID = userRepository.findById(user.getId());

        String userTempPassword = userTempID.getPassword();
        user.setPassword(userTempPassword);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public int findId(String username) {
        return userRepository.findId(username);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id);
    }
}
