package com.miguel.TaskAppBackend.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypter {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String encrypt(String password) {
        String passwordBrcypt = passwordEncoder.encode(password);
        return passwordBrcypt;
    }
}
