package com.example.application.persistence.document;

import com.example.application.persistence.pojo.HelpColumns;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public abstract class AbstractDocument extends HelpColumns {
    @MongoId
    private ObjectId id;
}
