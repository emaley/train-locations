package com.ewan.timetable.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    @ManyToMany
    private Set<Role> roles;

    @Tolerate
    public User() {
    }
}
