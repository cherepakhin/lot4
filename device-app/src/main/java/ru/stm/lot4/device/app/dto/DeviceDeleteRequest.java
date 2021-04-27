package ru.stm.lot4.device.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDeleteRequest {
    @NotBlank(message = "Firebase token is empty!")
    String firebaseToken;
}
