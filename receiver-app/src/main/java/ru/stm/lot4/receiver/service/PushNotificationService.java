package ru.stm.lot4.receiver.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;

import java.util.Date;
import java.util.List;

public interface PushNotificationService {
    PushNotificationEntity save(PushNotificationEntity pushNotificationEntity);
    void saveAll(List<PushNotificationEntity> pushNotificationEntityList);
    List<PushNotificationEntity> findAllByDateAndStatus(Date date, PushNotificationStatusEnum pushNotificationStatusEnum);
    Page<PushNotificationEntity> findAll(Pageable page);
    List<PushNotificationEntity> receiveActualAvailablePushNotification();

}
