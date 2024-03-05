package com.example.application.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_order")
@Data
public class Order extends AbstractEntity{

    @ManyToOne
    private User customer;

    @ManyToOne
    private User shifu;

    private String stateCode;

    private String reasonForEnding;

    private String picUrlArray;
}
