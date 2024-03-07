package com.example.application.persistence.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class RelationShifuService extends AbstractDocument{
    private ObjectId shifuId;
    private ObjectId serviceId;
}
