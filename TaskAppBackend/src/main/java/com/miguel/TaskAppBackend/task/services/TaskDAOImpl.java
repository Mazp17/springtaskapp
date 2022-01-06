package com.miguel.TaskAppBackend.task.services;

import com.miguel.TaskAppBackend.task.model.Task;
import com.miguel.TaskAppBackend.task.repository.TaskRepository;
import com.miguel.TaskAppBackend.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class TaskDAOImpl implements TaskDAO {
    @Autowired
    private TaskRepository repository;

    @Autowired
    private UserRepository rUser;

    @Override
    @Transactional(readOnly = true)
    public Optional<Task> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Task save(Task task) {
        return repository.save(task);
    }

    @Override
    @Transactional
    public Iterable<Task> findAll() {
        Iterable<Task> listTasks = repository.findAll();
        return listTasks;
    }

    @Override
    @Transactional
    public List<Task> findByIdUser(Integer  idUser) {
        return (repository).findByIdUser(idUser);
    }

    @Override
    @Transactional
    public void deletedById(Integer id) {
        repository.deleteById(id);
    }


}
