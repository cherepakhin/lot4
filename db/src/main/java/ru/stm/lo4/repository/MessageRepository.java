package ru.stm.lo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lo4.model.PushNotificationEntity;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<PushNotificationEntity, Long> {
}