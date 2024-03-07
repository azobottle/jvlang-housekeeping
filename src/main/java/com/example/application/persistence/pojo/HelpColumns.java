package com.example.application.persistence.pojo;


import lombok.Data;
import org.bson.types.ObjectId;

import java.util.Date;
@Data
public class HelpColumns {
    private Date createdTime;

    private ObjectId createdBy;

    private Date updatedTime;

    private ObjectId updatedBy;

    public void initHelpColumns(ObjectId userId) {
        Date now = new Date(System.currentTimeMillis());
        createdBy = userId;
        updatedBy = userId;
        createdTime = now;
        updatedTime = now;
    }

    public void updateHelpColumns(ObjectId userId) {
        Date now = new Date(System.currentTimeMillis());
        updatedBy = userId;
        updatedTime = now;
    }
}
