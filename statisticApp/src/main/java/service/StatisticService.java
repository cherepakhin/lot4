package service;
/**
 * 4.1 Отображение списка всех зарегистрированных в системе версий мобильного приложения,
 * с указанием количества регистраций и количества уникальных номеров телефонов для каждой версии.
 * 4.2 Отображения списка (с пейджингом) всех сообщений, ранее отправленных на указанный номер телефона.
 */

import ru.stm.lo4.model.ApplicationStatisticEntity;
import ru.stm.lot4.dto.MessageDto;

import java.util.List;

public interface StatisticService {
    ApplicationStatisticEntity getApplicationStat();
    List<MessageDto> getMessagesByPhone(String phone);
}
