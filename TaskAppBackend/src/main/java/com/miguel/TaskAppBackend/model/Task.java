package com.miguel.TaskAppBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "tasks")
@Getter @Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_task")
    private String nameTask;

    private String task;

    @Column(columnDefinition = "tinyint(1) default 0")
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

    public Task(Integer id, String nameTask, String task) {
        this.id = id;
        this.nameTask = nameTask;
        this.task = task;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id + ", nameTask='" + nameTask + '\'' +
                ", task='" + task + '\'' +
                ", deleted=" + deleted + ", deletedAt=" + deletedAt +
                ", completed=" + completed + ", completedAt=" + completedAt +
                ", createdAt=" + createdAt +
                '}';
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
}
