package com.miguel.TaskAppBackend.services.DAO;

import com.miguel.TaskAppBackend.model.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findById(Integer id);
    User save(User user);
    Iterable<User> findAll();
    void deleteById(Integer id);

}
