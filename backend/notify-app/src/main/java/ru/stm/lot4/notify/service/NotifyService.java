package ru.stm.lot4.notify.service;

import ru.stm.lot4.dto.PushNotificationRequest;

public interface NotifyService {
    String sendRequest(PushNotificationRequest pushNotificationRequest);
}
