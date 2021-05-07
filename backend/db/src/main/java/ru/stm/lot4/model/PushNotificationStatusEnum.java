package ru.stm.lot4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
public enum  PushNotificationStatusEnum implements Serializable {
    NOT_AVAILABLE(0),
    AVAILABLE(1);

    @Getter
    private int code;
}
