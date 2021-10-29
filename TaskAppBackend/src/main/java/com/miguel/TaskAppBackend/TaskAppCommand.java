package com.miguel.TaskAppBackend;

import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskAppCommand implements CommandLineRunner {
    @Autowired
    private UserDAO service;

    @Override
    public void run(String... args) throws Exception {
        User miguel = new User(null,
                "miguel",
                "miguel123",
                "miguel@zapata.com");
        User save = service.save(miguel);
        System.out.println(save.toString());
    }
}
