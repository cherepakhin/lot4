package ru.stm.lot4.statistic.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.stm.lot4.model.PushNotificationEntity;
import ru.stm.lot4.statistic.dto.MessageDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PushNotificationEntityMapper {
    @Mapping(source = "body", target = "message")
    @Mapping(source = "date", target = "time", qualifiedByName = "convertTime")
    MessageDto toDto(PushNotificationEntity pushNotificationEntity);

    @Named("convertTime")
    default ZonedDateTime toZonedDateTime(Date date) {
        if (Objects.isNull(date)) return LocalDateTime.MIN.atZone(ZoneId.of("Europe/Moscow"));
        return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
