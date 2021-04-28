package ru.stm.lot4.receiver.service;

/**
 * Серис по переодической отправки доступных push-уведомлений в PushNotificationSenderService
 * @see PushNotificationSenderService
 */
public interface PushNotificationSchedulerService {
    /**
     * Метод отправки доступных push-уведомлений в PushNotificationSenderService
     */
    void sendPushNotificationToKafka();
}
