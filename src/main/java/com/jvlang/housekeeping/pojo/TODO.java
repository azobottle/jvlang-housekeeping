package com.jvlang.housekeeping.pojo;

public final class TODO extends Error {
    public TODO() {
        this("这里的代码还没写: " + Thread.currentThread().getStackTrace()[3].toString());
    }

    public TODO(String message) {
        super(message);
    }
}
