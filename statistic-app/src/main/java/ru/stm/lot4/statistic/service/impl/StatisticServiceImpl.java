package ru.stm.lot4.statistic.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.stm.lot4.model.MobileApplicationStatisticEntity;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.model.PushNotificationStatusEnum;
import ru.stm.lot4.repository.MessageRepository;
import ru.stm.lot4.repository.MobileApplicationRepository;
import ru.stm.lot4.statistic.dto.MessageDto;
import ru.stm.lot4.statistic.service.StatisticService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final MobileApplicationRepository mobileApplicationRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<MobileApplicationStatisticEntity> getApplicationStat() {
        log.info("Get application statistic");
        return mobileApplicationRepository.getStatistic();
    }

    @Override
    public List<MessageDto> getMessagesByPhone(String phone, Pageable page) {
        log.info("Get received messages by phone {}", phone);
        List<PushNotificationEntity> messages = messageRepository.findReceivedMessagesByPhoneAndStatus(phone,
                PushNotificationStatusEnum.NOT_AVAILABLE.getCode(),
                page);
        log.info("Found {} messages", messages.size());
        return pushNotificationToDto(messages);
    }

    private List<MessageDto> pushNotificationToDto(List<PushNotificationEntity> messages) {
        log.info("Convert {} messages to dto", messages.size());
        return messages.stream()
                .map(message -> new MessageDto(message.getTitle(), message.getBody(), message.getData()))
                .collect(Collectors.toList());
    }
}
