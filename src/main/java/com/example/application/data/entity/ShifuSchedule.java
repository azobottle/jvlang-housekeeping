package com.example.application.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "t_shifu_schedule")
public class ShifuSchedule extends AbstractEntity{
    @ManyToOne
    private User shifu;

    private Date date;

    private Boolean availableMor;

    private Boolean availableAft;
}
