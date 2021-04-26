package ru.stm.lot4.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class AppDto {
    Long id;
    String token;
    String version;
}
