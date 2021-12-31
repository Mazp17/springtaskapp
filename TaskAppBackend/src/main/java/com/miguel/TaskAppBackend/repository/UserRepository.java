package com.miguel.TaskAppBackend.repository;

import com.miguel.TaskAppBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByName(String name);
    Optional<User> findById(Integer id);
}
