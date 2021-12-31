package com.miguel.TaskAppBackend.auth;

import com.miguel.TaskAppBackend.model.User;
import com.miguel.TaskAppBackend.repository.UserRepository;
import com.miguel.TaskAppBackend.services.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InfoAddToken implements TokenEnhancer {

    @Autowired
    private UserDAO repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = repository.findByEmail(authentication.getName());

        Map<String, Object> info = new HashMap<>();
        info.put("id", user.getId());
        info.put("username", user.getName());
        info.put("email", user.getEmail());
        info.put("password", user.getPassword());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
