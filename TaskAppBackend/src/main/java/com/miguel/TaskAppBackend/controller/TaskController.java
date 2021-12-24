package com.miguel.TaskAppBackend.controller;


import com.miguel.TaskAppBackend.exception.BadRequestException;
import com.miguel.TaskAppBackend.model.Task;
import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.services.DAO.TaskDAO;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskDAO taskS;

    private final UserDAO userDAO;


    @Autowired
    public TaskController(TaskDAO taskS, UserDAO userDAO) {
        this.taskS = taskS;
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

    //Metodo para actualizar tareas
    @PutMapping("/task/update/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) {
        Optional<Task> oTask = taskS.findById(id);

        Task taskUpdate = oTask.get();
        taskUpdate.setNameTask(task.getNameTask());
        taskUpdate.setTask(task.getTask());
        taskUpdate.setCompleted(task.isCompleted());

        return taskS.save(taskUpdate);
    }

    //metodo para marcar como completedas las tareas
    @PutMapping("/task/completed/{id}")
    public Task completedTask(@PathVariable Integer id) {
        Optional<Task> oTask = taskS.findById(id);

        Task taskUpdate = oTask.get();
        taskUpdate.setCompleted(true);

        taskUpdate.setCompletedAt(new Date());
        return taskS.save(taskUpdate);
    }

    @PutMapping("/task/noCompleted/{id}")
    public Task noCompletedTask(@PathVariable Integer id) {
        Optional<Task> oTask = taskS.findById(id);

        Task taskUpdate = oTask.get();
        taskUpdate.setCompleted(false);

        taskUpdate.setCompletedAt(null);
        return taskS.save(taskUpdate);
    }

    @PutMapping("/task/deleted/{id}")
    public Task deleted(@PathVariable Integer id) {
        Optional<Task> oTask = taskS.findById(id);
        Task taskUpdate = oTask.get();
        taskUpdate.setDeleted(true);
        taskUpdate.setDeletedAt(new Date());

        return  taskS.save(taskUpdate);
    }
    @PutMapping("/task/noDeleted/{id}")
    public Task noDeleted(@PathVariable Integer id) {
        Optional<Task> oTask = taskS.findById(id);
        Task taskUpdate = oTask.get();
        taskUpdate.setDeleted(false);
        taskUpdate.setDeletedAt(null);

        return  taskS.save(taskUpdate);
    }


}
