package com.project.farmsmart.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "roles")
    private String role;

    @ManyToMany
    @JoinTable(name = "")
    private List<User> users;
}
