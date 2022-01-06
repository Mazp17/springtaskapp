package com.miguel.TaskAppBackend.task.repository;

import com.miguel.TaskAppBackend.task.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    /*@Query("select task from Task task join fetch a.user usr where usr.id = ?1")*/
    @Query("select task from Task task " +
                "inner join task.user usr " +
            "where usr.id = ?1")
    List<Task> findByIdUser(Integer idUser);
}
