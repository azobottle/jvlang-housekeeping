package com.jvlang.housekeeping.util;

import dev.hilla.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@RequiredArgsConstructor
@AllArgsConstructor
public class Tuple3<V1, V2, V3> {
    @Nullable
    protected V1 v1;
    @Nullable
    protected V2 v2;
    @Nullable
    protected V3 v3;
}
