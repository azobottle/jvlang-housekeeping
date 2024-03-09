package com.example.application.persistence.document;

import com.example.application.persistence.pojo.Picture;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class Order extends AbstractDocument {

    private ObjectId customerId;

    private ObjectId shifuId;

    private String stateCode;

    private String reasonForEnding;

    private List<Picture> inspectionPicArray;
}
