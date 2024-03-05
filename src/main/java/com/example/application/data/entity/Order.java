package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_order")
public class Order extends AbstractEntity{

    @ManyToOne
    private User customer;

    @ManyToOne
    private User shifu;

    private String stateCode;

    private String reasonForEnding;

    private String picUrlArray;
}
