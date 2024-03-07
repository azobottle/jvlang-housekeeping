package com.example.application.persistence.document;

import com.example.application.persistence.pojo.Picture;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Service extends AbstractDocument {
    private String name;

    private Long price;

    private List<Picture> descriptionPicArray;

    private String description;

    private String link;

}
