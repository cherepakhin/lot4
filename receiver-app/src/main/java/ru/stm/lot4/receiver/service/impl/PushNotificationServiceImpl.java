package ru.stm.lot4.receiver.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;
import ru.stm.lot4.repository.PushNotificationRepository;
import ru.stm.lot4.receiver.service.PushNotificationService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PushNotificationServiceImpl implements PushNotificationService {

    private final PushNotificationRepository pushNotificationRepository;


    @Override
    public PushNotificationEntity save(PushNotificationEntity pushNotificationEntity) {
        return pushNotificationRepository.save(pushNotificationEntity);
    }

    @Override
    public void saveAll(List<PushNotificationEntity> pushNotificationEntityList) {
        pushNotificationRepository.saveAll(pushNotificationEntityList);
    }

    @Override
    public List<PushNotificationEntity> findAllByDateAndStatus(Date date, PushNotificationStatusEnum pushNotificationStatusEnum) {
        return pushNotificationRepository.findAllByDateAndStatus(date, pushNotificationStatusEnum);
    }

    @Override
    public Page<PushNotificationEntity> findAll(Pageable page) {
        return pushNotificationRepository.findAll(page);
    }

    @Override
    @Transactional
    public List<PushNotificationEntity> receiveActualAvailablePushNotification() {
        List<PushNotificationEntity> pushNotificationEntityList = findAllByDateAndStatus(
                        Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        PushNotificationStatusEnum.AVAILABLE);
        pushNotificationEntityList
                .forEach(pushNotificationEntity -> pushNotificationEntity
                        .setStatus(PushNotificationStatusEnum.NOT_AVAILABLE));
        saveAll(pushNotificationEntityList);
        return pushNotificationEntityList;
    }
}
