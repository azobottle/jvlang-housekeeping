package com.jvlang.housekeeping.pojo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
public class Picture {
    String url;
}
