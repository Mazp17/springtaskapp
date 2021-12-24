package com.miguel.TaskAppBackend.services.DAO;

import com.miguel.TaskAppBackend.model.Role;

public interface RoleDAO {

    Role findByName(String name);
}
