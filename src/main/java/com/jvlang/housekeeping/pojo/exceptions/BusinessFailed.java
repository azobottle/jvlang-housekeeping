package com.jvlang.housekeeping.pojo.exceptions;

import dev.hilla.exception.EndpointException;
import lombok.Getter;

import java.util.Map;

/**
 * 业务异常父类。
 */
public abstract class BusinessFailed extends EndpointException {

    public BusinessFailed(Throwable cause) {
        super(cause);
    }

    public BusinessFailed(String message, Object detail) {
        super(message, detail);
    }

    public BusinessFailed(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessFailed(String message, Throwable cause, Object detail) {
        super(message, cause, detail);
    }

    public BusinessFailed(String message) {
        super(message);
    }

    @Override
    public Map<String, Object> getSerializationData() {
        return super.getSerializationData();
    }
}
