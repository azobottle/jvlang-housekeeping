package com.example.application.persistence.document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class RelationShifuService extends AbstractDocument{
    private ObjectId shifuId;
    private ObjectId serviceId;
}
