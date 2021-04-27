package ru.stm.lot4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum  PushNotificationStatusEnum {
    NOT_AVAILABLE(0),
    AVAILABLE(1);

    @Getter
    private int code;
}
