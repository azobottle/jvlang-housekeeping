package com.example.application.persistence.document;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
public class Role extends AbstractDocument {
    private String name;

}
