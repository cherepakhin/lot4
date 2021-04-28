package ru.stm.lot4.receiver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.receiver.dto.PushNotificationRequest;

/**
 * Сервис преобразования объектов из:
 * PushNotificationDto в PushNotificationEntity и обратно
 * PushNotificationRequest в PushNotificationEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PushNotificationMapper {

    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy hh:mm:ss")
    @Mapping(source = "id", target = "id", ignore = true)
    PushNotificationDto toDTO(PushNotificationEntity entity);

    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy hh:mm:ss")
    PushNotificationEntity fromDTO(PushNotificationDto dto);

    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy hh:mm:ss")
    @Mapping(source = "phones", target = "phones", ignore = true)
    PushNotificationEntity fromRequest(PushNotificationRequest pushNotificationEntity);
}
