package com.miguel.TaskAppBackend.controller;

import com.miguel.TaskAppBackend.model.Role;
import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.services.DAO.RoleDAO;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

//TODO: Implementar metodos de inicio de sesión
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserDAO userService;

    @Autowired
    private RoleDAO roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public Iterable<User> findAll() {
        return userService.findAll();
    };

    @PostMapping(value = "/user/register/{typeUser}", consumes = {"application/json"})
    //@RequestMapping(method = RequestMethod.POST, path = "/user/register")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, @NotNull BindingResult result, @PathVariable Integer typeUser ) {

        User userNew = null;
        Map<String, Object> response = new HashMap<>();

        if(result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "Field: '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        User userSearch = userService.findByName(user.getName());

        if(userSearch != null) {
            response.put("error", "user already exists");
            response.put("message", "username is already exists");
            response.put("code", "userExists");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            String encriptPassword =  encriptPassword(user.getPassword());
            user.setPassword(encriptPassword);
            user.setEnabled(true);
            if(typeUser == 1) {
                Role role = roleService.findByName("ROLE_ADMIN");
                user.setRoles(Arrays.asList(role));
                 System.out.println("enabled is: " + user.getEnabled());
                userNew = userService.save(user);
            } else {
                response.put("message", "not specify typeUser in route or incorrect number in path, search in documentation api");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
            }

        } catch (DataAccessException e) {
            response.put("message", "An error occurred while inserting in the BD");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("message", "User successfully inserted");
        response.put("user", userNew);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    };

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        Map<String, Object> mensaje = new HashMap<>();
        User oUser = userService.findByEmail(email);

        mensaje.put("success", Boolean.TRUE);
        mensaje.put("user", oUser);
        return ResponseEntity.ok(mensaje);
    }

    public ResponseEntity<User> login(@Valid @RequestBody User user) {

        return null;
    }

    public String encriptPassword(String password) {
        String passwordBcrypt = passwordEncoder.encode(password);
        return passwordBcrypt;
    }
}
