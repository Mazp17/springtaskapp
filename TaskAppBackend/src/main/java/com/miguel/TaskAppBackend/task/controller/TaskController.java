package com.miguel.TaskAppBackend.task.controller;


import com.miguel.TaskAppBackend.exception.BadRequestException;
import com.miguel.TaskAppBackend.task.model.Task;
import com.miguel.TaskAppBackend.user.model.User;
import com.miguel.TaskAppBackend.task.services.TaskDAO;
import com.miguel.TaskAppBackend.user.services.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //@GetMapping("/task")
    /*public Iterable<Task> findAll() {
        return taskDAO.findAll();
    }*/


    @GetMapping("/task")
    public ResponseEntity<?> getTasks(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        User user = userDAO.findByEmail(authentication.getName());
        try {
            List<Task> tasks = user.getTasks();
            response.put("message", "Your tasks");
            response.put("tasks", tasks);
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.OK);
        } catch (Error e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage().concat(": ")
                    .concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //peticion POST para guardar tareas recibe los parametros:
    //Por PathVariable el idUser
    //por Request body (json):nameTask: string , task: string
    @PostMapping("/task")
    public ResponseEntity<?> saveTask(Authentication authentication,
                                      @RequestBody Task task) {
        Map<String, Object> response = new HashMap<>();
        User oUser = userDAO.findByEmail(authentication.getName());
        try {
            task.setUser(oUser);
            taskDAO.save(task);
            response.put("message", oUser.getName() + " Your task added");
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.CREATED);
        } catch (Error e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage().concat(": ")
                    .concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        User oUser = userDAO.findByEmail(authentication.getName());
        Optional<Task> oTask = taskDAO.findById(id);

        try {
            response.put("message", "Your task");
            response.put("task", oTask);
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.OK);
        } catch (Error e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage().concat(": ")
                    .concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Metodo para actualizar tareas
    @PutMapping("/task/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id,
                           @RequestBody Task task,
                           Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        User oUser = userDAO.findByEmail(authentication.getName());
        Optional<Task> oTask = taskDAO.findById(id);

        try {
            Task taskUpdate = oTask.get();
            taskUpdate.setNameTask(task.getNameTask());
            taskUpdate.setTask(task.getTask());
            taskUpdate.setCompleted(task.isCompleted());
            taskDAO.save(taskUpdate);
            response.put("message", oUser.getName() + " Your task identify with id" +
                    taskUpdate.getId() + " is modify");
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.OK);
        } catch (Error e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage().concat(": ")
                    .concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //metodo para marcar como completedas las tareas
    @PutMapping("/task/completed/{id}")
    public ResponseEntity<?> completedTask(@PathVariable Integer id,
                                           Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        User oUser = userDAO.findByEmail(authentication.getName());
        Optional<Task> oTask = taskDAO.findById(id);
        try {
            Task taskUpdate = oTask.get();
            if(taskUpdate.isCompleted()) {
                taskUpdate.setCompleted(false);
                taskUpdate.setCompletedAt(null);
                taskDAO.save(taskUpdate);
                response.put("message", oUser.getName() +
                        " Your task identify with id " +
                        taskUpdate.getId() + " is marked as not completed");
                return new ResponseEntity<Map<String, Object>>
                        (response, HttpStatus.OK);
            }
            taskUpdate.setCompleted(true);
            taskUpdate.setCompletedAt(new Date());
            taskDAO.save(taskUpdate);
            response.put("message", oUser.getName() + " Your task identify with id" +
                    taskUpdate.getId() + " is marked as completed");
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.OK);
        } catch (Error e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage().concat(": ")
                    .concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/task/deleted/{id}")
    public ResponseEntity<?> deleted(@PathVariable Integer id,
                                     Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        User oUser = userDAO.findByEmail(authentication.getName());
        Optional<Task> oTask = taskDAO.findById(id);

        try {
            Task taskUpdate = oTask.get();
            if(taskUpdate.isDeleted()) {
                taskUpdate.setDeleted(false);
                taskUpdate.setDeletedAt(null);
                taskDAO.save(taskUpdate);
                response.put("message", oUser.getName() + " Your task identify with id" +
                        taskUpdate.getId() + " has been marked as not deleted");
            } else {
                taskUpdate.setDeleted(true);
                taskUpdate.setDeletedAt(new Date());
                taskDAO.save(taskUpdate);
                response.put("message", oUser.getName() + " Your task identify with id" +
                        taskUpdate.getId() + " has been marked as deleted");
            }
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.OK);
        } catch (Error e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage().concat(": ")
                    .concat(e.getMessage()));
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String encript(String password) {
        String passwordBcrypt = passwordEncoder.encode(password);
        return passwordBcrypt;
    }


}
