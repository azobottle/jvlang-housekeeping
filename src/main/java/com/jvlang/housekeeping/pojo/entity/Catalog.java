package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import com.jvlang.housekeeping.pojo.Picture;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Catalog extends AbstractEntity {
    @Nullable
    private String text;

    @Nullable
    private Long parentCatalogId;

    @Nullable
    @JdbcTypeCode(SqlTypes.JSON)
    private Picture picture;

    @Nullable
    private DisplayStyle displayStyle;

    public enum DisplayStyle {
        Default, Cover
    }
}
