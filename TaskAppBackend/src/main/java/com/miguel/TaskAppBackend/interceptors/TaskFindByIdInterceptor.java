package com.miguel.TaskAppBackend.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguel.TaskAppBackend.task.model.Task;
import com.miguel.TaskAppBackend.task.services.TaskDAO;
import com.miguel.TaskAppBackend.user.model.User;
import com.miguel.TaskAppBackend.user.services.UserDAO;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/*
    This interceptor search tasks is not null
    and search to make the task the user
 */
public class TaskFindByIdInterceptor implements HandlerInterceptor {

    private UserDAO userDAO;

    private TaskDAO taskDAO;

    public TaskFindByIdInterceptor(UserDAO userDAO, TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Map<String, Object> responseMsg = new HashMap<>();
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        User oUser = userDAO.findByEmail(authentication.getName());
        Map pathVariables = (Map) request.getAttribute
                (HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String id = pathVariables.get("id").toString();
        Optional<Task> oTask = taskDAO.findById(Integer.valueOf(id));

        //search task is not null
        if(!oTask.isPresent()) {
            responseMsg.put("error", "task not exist");
            responseMsg.put("message", "The task is no exist in own BD");
            responseMsg.put("code", "1001");
            String json = new ObjectMapper().writeValueAsString(responseMsg);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(json);
            return false;
        }
        //search tasks is make to the user
        if(!Objects.equals(oUser.getId(), oTask.get().getUser().getId())) {
            responseMsg.put("error", "user is not own this task");
            responseMsg.put("message",
                    "the user is not own this task, u can only search your own tasks");
            responseMsg.put("code", "2001");
            String json = new ObjectMapper().writeValueAsString(responseMsg);
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(json);
            return false;
        }
        return true;
    }
}
