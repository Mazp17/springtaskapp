package com.miguel.TaskAppBackend.services.DAO;

import com.miguel.TaskAppBackend.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDAO {
    Optional<Task> findById(Integer id);
    Task save(Task task);
    Iterable<Task> findAll();
    List<Task> findByIdUser(Integer idUser);
    void deletedById(Integer id);
}
