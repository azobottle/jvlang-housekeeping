package com.jvlang.housekeeping.pojo.exceptions;

public class ScheduleFailed extends RuntimeException implements BusinessFailed{
    public ScheduleFailed(String m) {
        super(m);
    }
}
