package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "t_service")
public class Service extends AbstractEntity{
    @NotEmpty
    private String name;

    private Long price;

    private String picUrlArray;

    private String description;

    private String link;

    @ManyToMany(mappedBy = "learnedServiceList")
    private List<User> learnedShifuList;
}
