package com.example.application.persistence.pojo;

import lombok.Data;

import java.util.Date;


@Data
public class ShifuSchedule extends HelpColumns {
    private Date date;

    private Boolean availableMor;

    private Boolean availableAft;
}
