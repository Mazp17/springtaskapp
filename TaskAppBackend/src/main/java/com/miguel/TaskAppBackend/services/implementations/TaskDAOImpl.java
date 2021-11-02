package com.miguel.TaskAppBackend.services.implementations;

import com.miguel.TaskAppBackend.model.Task;
import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.repository.TaskRepository;
import com.miguel.TaskAppBackend.repository.UserRepository;
import com.miguel.TaskAppBackend.services.DAO.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
//TODO: Insert task in database with relation user_id
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
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deletedById(Integer id) {
        repository.deleteById(id);
    }
}