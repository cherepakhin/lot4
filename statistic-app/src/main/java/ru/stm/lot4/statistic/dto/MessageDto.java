package ru.stm.lot4.statistic.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "Модель сообщения пользователя")
public class MessageDto {
    @ApiModelProperty(dataType = "String", name = "title", notes = "Заголовок сообщения")
    private String title;
    @ApiModelProperty(dataType = "String", name = "message", notes = "Тело сообщения")
    private String message;
    @ApiModelProperty(dataType = "ZonedDateTime", name = "time", notes = "Время отправки сообщения")
    private ZonedDateTime time;
}
