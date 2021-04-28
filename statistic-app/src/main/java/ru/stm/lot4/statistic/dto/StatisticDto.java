package ru.stm.lot4.statistic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "Модель информации по статистике версии приложения")
public class StatisticDto {
    @ApiModelProperty(dataType = "String", name = "version", notes = "Версия приложения")
    private String version;
    @ApiModelProperty(dataType = "Long", name = "registrationsCount", notes = "Количество зарегистрированных пользователей")
    private Long registrationsCount;
    @ApiModelProperty(dataType = "Long", name = "phoneNumbersCount", notes = "Количество уникальных номеров телефона")
    private Long phoneNumbersCount;
}
