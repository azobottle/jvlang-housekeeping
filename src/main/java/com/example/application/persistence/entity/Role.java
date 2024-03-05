package com.example.application.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "t_role")
@Data
public class Role extends AbstractEntity{
    @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "roleList")
    private List<User> userList;
}
