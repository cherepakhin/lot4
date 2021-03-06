package ru.stm.lot4.receiver.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lot4.dto.PushNotificationRequest;
import ru.stm.lot4.model.PhoneEntity;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;
import ru.stm.lot4.receiver.mapper.PushNotificationMapper;
import ru.stm.lot4.receiver.service.PushNotificationService;
import ru.stm.lot4.repository.PhoneRepository;
import ru.stm.lot4.repository.PushNotificationRepository;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PushNotificationServiceImpl implements PushNotificationService {

    public static final PageRequest PAGE_REQUEST = PageRequest.of(0, 100);

    private final PushNotificationRepository pushNotificationRepository;
    private final PhoneRepository phoneRepository;
    private final PushNotificationMapper pushNotificationMapper;


    @Override
    public void save(PushNotificationEntity pushNotificationEntity) {
        pushNotificationRepository.save(pushNotificationEntity);
    }

    @Override
    @Transactional
    public PushNotificationEntity saveRequest(PushNotificationRequest pushNotificationRequest) {
        Set<PhoneEntity> phones = pushNotificationRequest.getPhones()
                .stream()
                .map(number -> phoneRepository.findByNumberAndIsActive(number, true))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (phones.isEmpty()) {
            log.warn("Not save push-notification! Phones not found");
            return null;
        }
        PushNotificationEntity pushNotificationEntity = pushNotificationMapper.fromRequest(pushNotificationRequest);
        pushNotificationEntity.setPhones(phones);
        pushNotificationEntity.setStatus(PushNotificationStatusEnum.AVAILABLE);
        return pushNotificationRepository.save(pushNotificationEntity);
    }

    @Override
    public void saveAll(List<PushNotificationEntity> pushNotificationEntityList) {
        pushNotificationRepository.saveAll(pushNotificationEntityList);
    }

    @Override
    public List<PushNotificationEntity> findAllByDateBeforeAndStatus(Date date, PushNotificationStatusEnum pushNotificationStatusEnum) {
        return pushNotificationRepository.findAllByDateBeforeAndStatus(date, pushNotificationStatusEnum, PAGE_REQUEST);
    }

    @Override
    public Page<PushNotificationEntity> findAll(Pageable page) {
        return pushNotificationRepository.findAll(page);
    }

    @Override
    @Transactional
    public List<PushNotificationEntity> receiveActualAvailablePushNotification() {
        List<PushNotificationEntity> pushNotificationEntityList = findAllByDateBeforeAndStatus(
                new Date(),
                PushNotificationStatusEnum.AVAILABLE);
        pushNotificationEntityList
                .forEach(pushNotificationEntity -> pushNotificationEntity
                        .setStatus(PushNotificationStatusEnum.NOT_AVAILABLE));
        saveAll(pushNotificationEntityList);
        return pushNotificationEntityList;
    }
}
