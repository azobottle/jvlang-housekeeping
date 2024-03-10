package com.example.application.persistence.document;

import com.example.application.persistence.pojo.ShifuSchedule;
import dev.hilla.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class User extends AbstractDocument {
    private String name;
    @Nullable
    private String openid;
    @Nullable
    private String iconURL;

    private String phoneNumber;

    private String password;
    @Nullable
    private List<ShifuSchedule> shifuSchedules;

}
