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
public class Tuple3<A, B, C> {
    @Nullable
    protected A a;
    @Nullable
    protected B b;
    @Nullable
    protected C c;
}
