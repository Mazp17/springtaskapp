package com.miguel.TaskAppBackend.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguel.TaskAppBackend.exception.BadRequestException;
import com.miguel.TaskAppBackend.user.model.User;
import com.miguel.TaskAppBackend.user.services.UserDAO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
    Marked is a deprecated because is not necessary use
*/
@Deprecated
public class TaskInterceptor implements HandlerInterceptor {

    private UserDAO userDAO;

    public TaskInterceptor(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Map<String, Object> responseMsg = new HashMap<>();
        System.out.println("Your email is: " + authentication.getName());

        User oUser = userDAO.findByEmail(authentication.getName());
        if(oUser == null) {
            responseMsg.put("error", "user not exist");
            responseMsg.put("message", "The user is no exist in own BD");
            responseMsg.put("code", "1001");

            String json = new ObjectMapper().writeValueAsString(responseMsg);

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(json);
            return false;
        }
        System.out.println("Your email is: " + authentication.getName());

        System.out.println("prehanlde executed");
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
