package ru.stm.lot4.dto;

import lombok.*;

import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MessageDto {
    Long id;
    String title = "";
    String body = "";
    Set<PhoneDto> phones = new HashSet<>();
}
