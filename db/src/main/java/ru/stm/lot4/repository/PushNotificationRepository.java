package ru.stm.lot4.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;

import java.util.Date;
import java.util.List;

import static ru.stm.lot4.constants.QueryConstants.SELECT_RECEIVED_MESSAGES_BY_PHONE;

@Repository
@Transactional
public interface PushNotificationRepository extends JpaRepository<PushNotificationEntity, Long> {

    List<PushNotificationEntity> findAllByDateAndStatus(Date date, PushNotificationStatusEnum status);

    @Query(nativeQuery = true, value = SELECT_RECEIVED_MESSAGES_BY_PHONE)
    List<PushNotificationEntity> findReceivedMessagesByPhoneAndStatus(String phone, int status, Pageable page);
}
