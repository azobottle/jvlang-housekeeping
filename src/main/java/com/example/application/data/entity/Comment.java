package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_comment")
public class Comment extends AbstractEntity {
    private String commentType;

    private Long objectId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Comment comment;

    private String picUrlArray;

    private String commentState;
}
