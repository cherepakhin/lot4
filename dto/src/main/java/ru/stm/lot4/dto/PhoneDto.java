package ru.stm.lot4.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PhoneDto {
    Long id;
    String number;
    AppDto app;
}
