package com.example.application.persistence.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Date createdTime;

    @NotNull
    private Long createdBy;

    @NotNull
    private Date updatedTime;

    @NotNull
    private Long updatedBy;

    public void initHelpColumns(Long userId) {
        Date now = new Date(System.currentTimeMillis());
        createdBy = userId;
        updatedBy = userId;
        createdTime = now;
        updatedTime = now;
    }

    public void updateHelpColumns(Long userId) {
        Date now = new Date(System.currentTimeMillis());
        updatedBy = userId;
        updatedTime = now;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
