package com.example.application.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_comment")
@Data
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
