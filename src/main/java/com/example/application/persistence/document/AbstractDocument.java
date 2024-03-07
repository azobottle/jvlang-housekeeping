package com.example.application.persistence.document;

import com.example.application.persistence.pojo.HelpColumns;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document
public abstract class AbstractDocument extends HelpColumns {
    @MongoId
    private ObjectId id;
}
