package com.example.application.persistence.document;

import com.example.application.configuration.JsonConfig;
import com.example.application.util.Jsons;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.lang.Nullable;

import java.util.Map;


@Data
@SuperBuilder(toBuilder = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public abstract class AbstractDocument implements ReadObjectId {
    /**
     * 返回给UI的id。
     */
    @JsonProperty("id")
    @Nullable
    public String getId() {
        return _object_id == null ? null : _object_id.toString();
    }

    @JsonProperty("id")
    public void setId(@dev.hilla.Nullable String id) {
        if (id == null || id.isBlank()) {
            // 从 hilla 创建时
            _object_id = null;
        } else {
            // 从 hilla 修改时
            try {
                _object_id = new ObjectId(id);
            } catch (Throwable err) {
                throw new IllegalArgumentException("Invalid `id` property format , class is " +
                        this.getClass().getSimpleName(),
                        err
                );
            }
        }
    }

    @JsonProperty(value = "_id", access = JsonProperty.Access.WRITE_ONLY)
    @SneakyThrows
    public void __set_id(Object _id) {
        log.debug("deserialize _id from set_id({} {})", _id == null ? "Void" : _id.getClass().getName(), _id);
        if (_id instanceof Map<?, ?> __id && __id.keySet().isEmpty()) {
            // ignore hilla input param
            return;
        }
        var m = JsonConfig.getGlobalObjectMapper();
        _object_id = Jsons.deserializeObjectId(m.readTree(m.writeValueAsString(_id)));
    }

    @SuppressWarnings("NullableProblems")
    @MongoId
    @Nullable
    @JsonIgnore
    protected ObjectId _object_id;

    public ObjectId read_object_id() {
        return _object_id;
    }

    @Version
    protected long _optimistic_locking;
}
