package ru.stm.lot4.statistic.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.stm.lot4.model.MobileApplicationStatisticEntity;
import ru.stm.lot4.statistic.dto.StatisticDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MobileApplicationStatisticEntityMapper {

    @Mapping(source = "appVersion", target = "version")
    StatisticDto toDto(MobileApplicationStatisticEntity statisticEntity);
}
