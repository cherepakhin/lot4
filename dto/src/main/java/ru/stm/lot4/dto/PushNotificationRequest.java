package ru.stm.lot4.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PushNotificationRequest {
    String id="";
    @NotBlank
    String title;
    @NotBlank
    String body;
    Set<String> phones = new HashSet<>();
    @NotNull
    Date date;
}
