package com.miguel.TaskAppBackend.controller;


import com.miguel.TaskAppBackend.exception.BadRequestException;
import com.miguel.TaskAppBackend.model.Task;
import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.services.DAO.TaskDAO;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskDAO taskS;

    private final UserDAO userDAO;


    @Autowired
    public TaskController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @GetMapping("/task")
    public Iterable<Task> findAll() {
        return taskS.findAll();
    }

    //Peticion GET para obtener las tareas por cada usuario
    @GetMapping("/task/{idUser}")
    public List<Task> findByIdUser(@PathVariable Integer idUser) {
        return taskS.findByIdUser(idUser);
    }

    //peticion POST para guardar tareas recibe los parametros:
    //Por PathVariable el idUser
    //por Request body (json): id (que es simpre de tipo NULL), nameTask, task y completed
    @PostMapping("/task/{idUser}")
    public Task saveTask(@PathVariable Integer idUser, @RequestBody Task task) {
        Optional<User> oUser = userDAO.findById(idUser);
        if(!oUser.isPresent()) {
            throw new BadRequestException(String.format("El usuario con id %d no existe", idUser));
        }
        User user = oUser.get();
        task.setUser(user);

        return taskS.save(task);
    }





}
