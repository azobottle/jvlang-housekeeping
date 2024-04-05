package com.jvlang.housekeeping.pojo.entity;

import com.jvlang.housekeeping.pojo.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Good extends AbstractEntity {
    String priceAtFirstSee;

    String titleLongText;
}
