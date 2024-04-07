package com.jvlang.housekeeping.pojo.exceptions;

import dev.hilla.exception.EndpointException;

public class CrudFailed extends BusinessFailed {
    public CrudFailed(String message) {
        super(message);
    }

    public CrudFailed(String message, Throwable cause) {
        super(message, cause);
    }
}
