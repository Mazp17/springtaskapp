package com.miguel.TaskAppBackend.repository;

import com.miguel.TaskAppBackend.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    /*@Query("select task from Task task join fetch a.user usr where usr.id = ?1")*/
    @Query("select task from Task task " +
                "inner join task.user usr " +
            "where usr.id = ?1")
    List<Task> findByIdUser(Integer idUser);
}
