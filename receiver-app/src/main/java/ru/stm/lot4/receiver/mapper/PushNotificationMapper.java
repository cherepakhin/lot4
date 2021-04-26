package ru.stm.lot4.receiver.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.stm.lot4.dto.PushNotificationDto;
import ru.stm.lot4.model.PushNotificationEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PushNotificationMapper {

    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy hh:mm:ss")
    PushNotificationDto toDTO(PushNotificationEntity entity);

    @Mapping(source = "date", target = "date", dateFormat = "dd-MM-yyyy hh:mm:ss")
    PushNotificationEntity fromDTO(PushNotificationDto dto);

}
