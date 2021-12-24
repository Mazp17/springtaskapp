package com.miguel.TaskAppBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Getter @Setter
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_task")
    private String nameTask;

    private String task;
    private boolean deleted;

    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean completed;

    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "deleted_at")
    private Date deletedAt;
    @ManyToOne(
            optional = true,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "FK_USER_ID")
    )
    private User user;

    public Task() {
    }

    public Task(Integer id, String nameTask, String task, boolean completed, boolean deleted) {

        this.id = id;
        this.nameTask = nameTask;
        this.task = task;
        this.completed = completed;
        this.deleted = deleted;
    }

}
