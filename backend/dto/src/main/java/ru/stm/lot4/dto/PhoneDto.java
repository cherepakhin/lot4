package ru.stm.lot4.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneDto {
    Long id;
    @Pattern(regexp = "^((\\+7|7|8|)+([0-9]){10})$", message = "Номер не корректен")
    String number;
    @NotBlank(message = "Токен не должен быть пустым!")
    String token;
    MobileApplicationDto app;
}
