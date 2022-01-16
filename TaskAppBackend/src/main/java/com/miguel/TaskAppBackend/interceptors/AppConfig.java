package com.miguel.TaskAppBackend.interceptors;

import com.miguel.TaskAppBackend.task.services.TaskDAO;
import com.miguel.TaskAppBackend.user.services.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    public UserDAO userDAO;

    @Autowired
    public TaskDAO taskDAO;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TaskFindByIdInterceptor(userDAO, taskDAO))
                .addPathPatterns("/api/task/{id}",
                        "/api/task/completed/{id}",
                        "/api/task/deleted/{id}",
                        "/api/task/update/{id}");
    }
}
