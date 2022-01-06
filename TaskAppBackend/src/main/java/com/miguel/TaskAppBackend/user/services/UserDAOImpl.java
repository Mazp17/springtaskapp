package com.miguel.TaskAppBackend.user.services;

import com.miguel.TaskAppBackend.user.model.User;
import com.miguel.TaskAppBackend.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDAOImpl implements UserDetailsService, UserDAO {

    private Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null) {
            logger.error("Error in loggin, user not exists: '" + email + "'");
            throw new UsernameNotFoundException("Error in loggin, user not exists: '" + email + "'");
        }


        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                authorities
        );
    }


    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {

        return repository.findByEmail(email);
    }

    @Override
    public User findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Transactional
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }


}
