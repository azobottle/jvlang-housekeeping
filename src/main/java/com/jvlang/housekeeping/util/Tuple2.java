package com.jvlang.housekeeping.util;

import dev.hilla.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 类似于 Map.entry ，但是可空。
 * @param <A>
 * @param <B>
 */
@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class Tuple2<A, B> {
    @Nullable
    protected A a;
    @Nullable
    protected B b;
}
