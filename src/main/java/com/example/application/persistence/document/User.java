package com.example.application.persistence.document;

import com.example.application.persistence.pojo.ShifuSchedule;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class User extends AbstractDocument {
    private String name;

    private String openid;

    private String iconURL;

    private String phoneNumber;

    private String password;

    private List<ShifuSchedule> shifuSchedules;

}
