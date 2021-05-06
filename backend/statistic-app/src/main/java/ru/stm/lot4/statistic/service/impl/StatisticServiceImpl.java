package ru.stm.lot4.statistic.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.stm.lot4.model.MobileApplicationStatisticEntity;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;
import ru.stm.lot4.repository.MobileApplicationRepository;
import ru.stm.lot4.repository.PushNotificationRepository;
import ru.stm.lot4.statistic.dto.MessageDto;
import ru.stm.lot4.statistic.dto.StatisticDto;
import ru.stm.lot4.statistic.service.StatisticService;
import ru.stm.lot4.statistic.service.mapper.MobileApplicationStatisticEntityMapper;
import ru.stm.lot4.statistic.service.mapper.PushNotificationEntityMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final MobileApplicationRepository mobileApplicationRepository;
    private final MobileApplicationStatisticEntityMapper statisticEntityMapper;
    private final PushNotificationEntityMapper pushNotificationEntityMapper;
    private final PushNotificationRepository pushNotificationRepository;

    @Override
    public List<StatisticDto> getApplicationStat() {
        log.info("Get application statistic");
        List<MobileApplicationStatisticEntity> appStatistic = mobileApplicationRepository.getStatistic();
        log.info("Found {} versions of application", appStatistic.size());
        return appStatistic.stream().map(statisticEntityMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getMessagesByPhone(String phone, Pageable page) {
        log.info("Get received messages by phone {}", phone);
        List<PushNotificationEntity> messages = pushNotificationRepository.findReceivedMessagesByPhoneAndStatus(phone,
                PushNotificationStatusEnum.NOT_AVAILABLE.getCode(),
                page);
        log.info("Found {} messages", messages.size());
        return messages.stream().map(pushNotificationEntityMapper::toDto).collect(Collectors.toList());
    }

}
