package com.example.application.persistence.document;

import com.example.application.persistence.pojo.HelpColumns;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.hilla.crud.filter.Filter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.lang.Nullable;


import java.util.Optional;


@SuppressWarnings("NullableProblems")
@EqualsAndHashCode(callSuper = true)
@Document
public abstract class AbstractDocument extends HelpColumns {
    /**
     * 返回给UI的id。
     */
    @JsonProperty("id")
    @Nullable
    public String getId() {
        return _id == null ? null : _id.toHexString();
    }

    @JsonProperty("id")
    public void setId(@Nullable String id) {
        set_id(id);
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public void set_id(@Nullable String id) {
        _id = id == null ? null : new ObjectId(id);
    }

    @MongoId
    @Nullable
    protected ObjectId _id;
}
