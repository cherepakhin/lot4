package ru.stm.lot4.statistic.service;
/**
 * 4.1 Отображение списка всех зарегистрированных в системе версий мобильного приложения,
 * с указанием количества регистраций и количества уникальных номеров телефонов для каждой версии.
 * 4.2 Отображения списка (с пейджингом) всех сообщений, ранее отправленных на указанный номер телефона.
 */

import org.springframework.data.domain.Pageable;
import ru.stm.lo4.model.MobileApplicationStatisticEntity;
import ru.stm.lot4.statistic.dto.MessageDto;

import java.util.List;

public interface StatisticService {
    List<MobileApplicationStatisticEntity> getApplicationStat();
    List<MessageDto> getMessagesByPhone(String phone, Pageable page);
}
