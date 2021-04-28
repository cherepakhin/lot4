package ru.stm.lot4.device.app.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель запроса на удаление девайса")
public class DeviceDeleteRequest {
    @NotBlank(message = "Firebase token is empty!")
    String firebaseToken;
}
