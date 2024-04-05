package com.jvlang.housekeeping.util;

import dev.hilla.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 类似于 Map.entry ，但是可空。
 *
 * @param <V1>
 * @param <V2>
 */
@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class Tuple2<V1, V2> {
    @Nullable
    protected V1 v1;
    @Nullable
    protected V2 v2;
}
