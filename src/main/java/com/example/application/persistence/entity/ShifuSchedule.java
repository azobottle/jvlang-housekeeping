package com.example.application.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "t_shifu_schedule")
@Data
public class ShifuSchedule extends AbstractEntity{
    @ManyToOne
    private User shifu;

    private Date date;

    private Boolean availableMor;

    private Boolean availableAft;
}
