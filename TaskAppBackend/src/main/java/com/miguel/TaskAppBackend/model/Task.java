package com.miguel.TaskAppBackend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name_task")
    private String nameTask;

    private String task;

    private boolean completed;
    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "created_at")
    private Date createdAt;
    @ManyToOne(
            optional = false,
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

    public Task(int id, String nameTask, String task, boolean completed, Date completedAt, Date createdAt, User user) {
        this.id = id;
        this.nameTask = nameTask;
        this.task = task;
        this.completed = completed;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        this.user = user;
    }

    @PrePersist
    private void prePersist() {
        this.createdAt = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        this.completedAt = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", nameTask='" + nameTask + '\'' +
                ", task='" + task + '\'' +
                ", completed=" + completed +
                ", completedAt=" + completedAt +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
