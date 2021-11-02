package com.miguel.TaskAppBackend.controller;


import com.miguel.TaskAppBackend.exception.BadRequestException;
import com.miguel.TaskAppBackend.model.Task;
import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.services.DAO.TaskDAO;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskDAO taskD;

    private final UserDAO userDAO;


    @Autowired
    public TaskController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/task/{idUser}")
    public Task saveTask(@PathVariable Integer idUser, @RequestBody Task task) {
        Optional<User> oUser = userDAO.findById(idUser);
        User user = oUser.get();
        task.setUser(user);

        return taskD.save(task);
    }





}
