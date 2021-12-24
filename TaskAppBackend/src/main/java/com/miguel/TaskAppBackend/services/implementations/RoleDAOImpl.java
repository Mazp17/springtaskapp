package com.miguel.TaskAppBackend.services.implementations;

import com.miguel.TaskAppBackend.model.Role;
import com.miguel.TaskAppBackend.repository.RoleRepository;
import com.miguel.TaskAppBackend.services.DAO.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    RoleRepository repository;

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }
}
