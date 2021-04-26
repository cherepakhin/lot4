package service.impl;

import ru.stm.lo4.model.ApplicationStatisticEntity;
import ru.stm.lo4.repository.AppRepository;
import ru.stm.lo4.repository.MessageRepository;
import ru.stm.lot4.dto.MessageDto;
import service.StatisticService;

import java.util.List;

public class StatisticServiceImpl implements StatisticService {
    private final AppRepository appRepository;
    private final MessageRepository messageRepository;

    public StatisticServiceImpl(AppRepository appRepository, MessageRepository messageRepository) {
        this.appRepository = appRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public ApplicationStatisticEntity getApplicationStat() {
        return null;
    }

    @Override
    public List<MessageDto> getMessagesByPhone(String phone) {
        return null;
    }
}
