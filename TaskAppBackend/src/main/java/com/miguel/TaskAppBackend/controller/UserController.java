package com.miguel.TaskAppBackend.controller;

import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserDAO userS;

    @GetMapping("/user/")
    public Iterable<User> findAll() {
        return userS.findAll();
    };

    @PostMapping("/user/")
    public User saveUser(@RequestBody User user) {
      return userS.save(user);
    };
}
