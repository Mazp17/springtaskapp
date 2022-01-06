package com.miguel.TaskAppBackend.task.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.miguel.TaskAppBackend.GlobalVars;
import com.miguel.TaskAppBackend.user.model.User;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "tasks")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
