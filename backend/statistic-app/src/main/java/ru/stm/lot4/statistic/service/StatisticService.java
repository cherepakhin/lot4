package ru.stm.lot4.statistic.service;

import org.springframework.data.domain.Pageable;
import ru.stm.lot4.statistic.dto.MessageDto;
import ru.stm.lot4.statistic.dto.StatisticDto;

import java.util.List;

/**
 * Сервис для получения статистики приложения
 */
public interface StatisticService {
    /**
     * Метод получения общей статитики приложения
     * @return список статистики приложения по версиям
     */
    List<StatisticDto> getApplicationStat();

    /**
     * Метод получения всех отправленных сообщений пользователя по номеру телефона
     * @param phone номер телефона пользователя
     * @param page пейджинг
     * @return список отправленных сообщений пользователя
     */
    List<MessageDto> getMessagesByPhone(String phone, Pageable page);
}
