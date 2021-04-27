package ru.stm.lot4.device.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRegistryRequest {
    @NotBlank(message = "Empty firebase token!")
    private String firebaseToken;
    @NotBlank(message = "Empty number!")
    @Pattern(regexp = "^((\\+7|7|8|)+([0-9]){10})$", message = "Not valid number!")
    private String number;
    @NotBlank(message = "Empty version!")
    private String version;
}
