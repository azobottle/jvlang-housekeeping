package com.example.application.persistence.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RelationUserRole extends AbstractDocument {
    private ObjectId userId;
    private ObjectId roleId;
}
