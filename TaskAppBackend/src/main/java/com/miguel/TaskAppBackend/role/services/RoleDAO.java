package com.miguel.TaskAppBackend.role.services;

import com.miguel.TaskAppBackend.role.model.Role;

public interface RoleDAO {

    Role findByName(String name);
}
