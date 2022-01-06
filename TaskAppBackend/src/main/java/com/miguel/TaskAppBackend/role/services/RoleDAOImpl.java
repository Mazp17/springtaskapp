package com.miguel.TaskAppBackend.role.services;

import com.miguel.TaskAppBackend.role.model.Role;
import com.miguel.TaskAppBackend.role.repository.RoleRepository;
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
