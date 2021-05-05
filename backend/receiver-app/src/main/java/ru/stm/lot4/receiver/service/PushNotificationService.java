package ru.stm.lot4.receiver.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;

import java.util.Date;
import java.util.List;

/**
 * Сервис для работы с базой данных
 */
public interface PushNotificationService {
    /**
     * Преобразует push-уведомление dto в entity и сохраняет
     *
     * @param pushNotificationRequest - puh-уведомление от пользователя
     */
    PushNotificationEntity saveRequest(PushNotificationRequest pushNotificationRequest);

    /**
     * Метод для сохранения push-уведомления в базу
     *
     * @param pushNotificationEntity - push-уведомление
     */
    void save(PushNotificationEntity pushNotificationEntity);

    /**
     * Метод для транзакционного сохранения push-уведомлений в базе
     *
     * @param pushNotificationEntityList - push-уведомления
     */
    void saveAll(List<PushNotificationEntity> pushNotificationEntityList);

    /**
     * @param date                       - дата
     * @param pushNotificationStatusEnum - статус push-уведомления
     * @return - список push-уведомлений
     */
    List<PushNotificationEntity> findAllByDateBeforeAndStatus(Date date, PushNotificationStatusEnum pushNotificationStatusEnum);

    /**
     * Метод для поиска push-уведомлений с пагинацией
     *
     * @param page - пагинация
     * @return - push-уведомления
     */
    Page<PushNotificationEntity> findAll(Pageable page);

    /**
     * Метод получения не отправленых push-уведомлений до текущей даты
     *
     * @return push-уведомления
     */
    List<PushNotificationEntity> receiveActualAvailablePushNotification();

}
