package com.example.application.persistence.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class RelationUserRole extends AbstractDocument {
    private ObjectId userId;
    private ObjectId roleId;
}
