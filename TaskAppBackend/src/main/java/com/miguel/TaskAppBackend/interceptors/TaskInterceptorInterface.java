package com.miguel.TaskAppBackend.interceptors;

import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TaskInterceptorInterface extends HandlerInterceptor {
    boolean preHandle(HttpServletRequest request,
                      HttpServletResponse response,
                      Authentication authentication,
                      Object handler) throws Exception;

    /*
        public ResponseEntity<?> preHandle(HttpServletRequest request,
                                        Authentication authentication,
                                        Object handler) {
            Map<String, Object> response = new HashMap<>();
            User oUser = userDAO.findByEmail(authentication.getName());
            if(oUser == null) {
                response.put("error", "user not exist");
                response.put("message", "The user is no exist in own BD");
                response.put("code", "1001");
                return new ResponseEntity<Map<String, Object>>
                        (response, HttpStatus.BAD_REQUEST);
            }
            response.put("error", "user exist");
            return new ResponseEntity<Map<String, Object>>
                    (response, HttpStatus.OK);
        }*/
    /*
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Authentication authentication,
                             Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Map<String, Object> responseMsg = new HashMap<>();

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

        System.out.println("prehanlde executed");
        return true;
    }*/

}
