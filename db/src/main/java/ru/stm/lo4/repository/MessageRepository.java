package ru.stm.lo4.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lo4.model.PushNotificationEntity;

import java.util.List;

import static ru.stm.lo4.constants.QueryConstants.SELECT_RECEIVED_MESSAGES_BY_PHONE;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<PushNotificationEntity, Long> {
    @Query(nativeQuery = true, value = SELECT_RECEIVED_MESSAGES_BY_PHONE)
    List<PushNotificationEntity> findReceivedMessagesByPhoneAndStatus(String phone, int status, Pageable page);
}
