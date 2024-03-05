package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "t_role")
public class Role extends AbstractEntity{
    @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "roleList")
    private List<User> userList;
}
