package com.example.sendflow.enums;

import lombok.Getter;

@Getter
public enum Period {
    MONTHLY(1),  QUARTERLY(3),YEARLY(12);
    private final int months;

    Period(int months) {
        this.months = months;
    }

}
