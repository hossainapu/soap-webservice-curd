package com.hossain.business.common;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE(1),
    INACTIVE(2);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public static Status getStatus(int value) {
        for(Status status : Status.values()) {
            if(status.value == value) {
                return status;
            }
        }
        return null;
    }
}
