package com.example.application.persistence.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;


@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class Role extends AbstractDocument {
    private String name;

    private String description;
}
