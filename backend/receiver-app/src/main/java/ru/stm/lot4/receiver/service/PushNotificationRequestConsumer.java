package ru.stm.lot4.receiver.service;

import ru.stm.lot4.dto.PushNotificationRequest;

public interface PushNotificationRequestConsumer {
    void consume(PushNotificationRequest pushNotificationRequest);
}
