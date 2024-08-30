package com.app.springsecurity.service;

import com.app.springsecurity.dao.UserRepository;
import com.app.springsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User save(User user) {

        user.setPassword(encoder.encode(user.getPassword()));

        return repository.save(user);
    }
}
