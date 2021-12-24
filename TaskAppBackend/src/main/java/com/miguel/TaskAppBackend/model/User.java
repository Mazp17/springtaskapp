package com.miguel.TaskAppBackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String name;

    @NotEmpty
    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private Set<Task> tasks;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id", referencedColumnName = "id"
            )
    )
    private List<Role> roles;

    @Column(columnDefinition = "boolean default 1")
    private boolean enabled;

    public User() {
    }

    public User(Integer id, String name, String password, String email, boolean enabled) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.enabled = enabled;

    }

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    @JsonBackReference
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    //@org.codehaus.jackson.annotate.JsonIgnore
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", roles=" + roles +
                ", enabled=" + enabled +
                '}';
    }

}

