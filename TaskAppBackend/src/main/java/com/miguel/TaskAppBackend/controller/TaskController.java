package com.miguel.TaskAppBackend.controller;


import com.miguel.TaskAppBackend.exception.BadRequestException;
import com.miguel.TaskAppBackend.model.Task;
import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.services.DAO.TaskDAO;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    //Peticion GET para obtener las tareas por cada usuario
    @GetMapping("/task/{idUser}")
    public List<Task> findByIdUser(@PathVariable Integer idUser) {
        return taskDAO.findByIdUser(idUser);
    }

    @PostMapping("/task")
    public ResponseEntity<?> findByUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        User userSearch = userDAO.findByEmail(user.getEmail());
        if(userSearch == null) {
            response.put("error", "user not exist");
            response.put("message", "The user is no exist in own BD");
            response.put("code", "10001");
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.BAD_REQUEST);
        }
        try {
            if(Objects.equals(user.getEmail() + user.getPassword(),
                    userSearch.getEmail() + userSearch.getPassword())) {
                List<Task> tasks = userSearch.getTasks();

                response.put("message", "User exists");
                response.put("tasks", tasks);
                return new ResponseEntity<Map<String, Object>>
                        (response, HttpStatus.OK);
            }
            response.put("error", "Credentials incorrect");
            response.put("message", "Email or password is incorrect");
            response.put("code", "1003");
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.BAD_REQUEST);
            } catch (DataAccessException e) {
            response.put("message", "An error occurred");
            response.put("error", e.getMessage().concat(": ")
                    .concat(e.getMostSpecificCause()
                            .getMessage()));
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //peticion POST para guardar tareas recibe los parametros:
    //Por PathVariable el idUser
    //por Request body (json):nameTask: string , task: string
    @PostMapping("/task/{idUser}")
    public Task saveTask(@PathVariable Integer idUser, @RequestBody Task task) {
        Optional<User> oUser = userDAO.findById(idUser);
        if(!oUser.isPresent()) {
            throw new BadRequestException
                    (String.format("El usuario con id %d no existe", idUser));
        }
        User user = oUser.get();
        task.setUser(user);

        return taskDAO.save(task);
    }

    //Metodo para actualizar tareas
    @PutMapping("/task/update/{id}")
    public Task updateTask(@PathVariable Integer id, @RequestBody Task task) {
        Optional<Task> oTask = taskDAO.findById(id);

        Task taskUpdate = oTask.get();
        taskUpdate.setNameTask(task.getNameTask());
        taskUpdate.setTask(task.getTask());
        taskUpdate.setCompleted(task.isCompleted());

        return taskDAO.save(taskUpdate);
    }

    //metodo para marcar como completedas las tareas
    @PutMapping("/task/completed/{id}")
    public Task completedTask(@PathVariable Integer id) {
        Optional<Task> oTask = taskDAO.findById(id);

        Task taskUpdate = oTask.get();
        taskUpdate.setCompleted(true);

        taskUpdate.setCompletedAt(new Date());
        return taskDAO.save(taskUpdate);
    }

    @PutMapping("/task/noCompleted/{id}")
    public Task noCompletedTask(@PathVariable Integer id) {
        Optional<Task> oTask = taskDAO.findById(id);

        Task taskUpdate = oTask.get();
        taskUpdate.setCompleted(false);

        taskUpdate.setCompletedAt(null);
        return taskDAO.save(taskUpdate);
    }

    @PutMapping("/task/deleted/{id}")
    public Task deleted(@PathVariable Integer id) {
        Optional<Task> oTask = taskDAO.findById(id);
        Task taskUpdate = oTask.get();
        taskUpdate.setDeleted(true);
        taskUpdate.setDeletedAt(new Date());

        return  taskDAO.save(taskUpdate);
    }
    @PutMapping("/task/noDeleted/{id}")
    public Task noDeleted(@PathVariable Integer id) {
        Optional<Task> oTask = taskDAO.findById(id);
        Task taskUpdate = oTask.get();
        taskUpdate.setDeleted(false);
        taskUpdate.setDeletedAt(null);

        return  taskDAO.save(taskUpdate);
    }

    public String encript(String password) {
        String passwordBcrypt = passwordEncoder.encode(password);
        return passwordBcrypt;
    }


}
