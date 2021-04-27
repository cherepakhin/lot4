package ru.stm.lot4.sender.firebasemessage;

import lombok.Getter;
import lombok.Setter;
import ru.stm.lot4.dto.PhoneDto;
import ru.stm.lot4.dto.PushNotificationDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FireBaseMessage {
    Notification notification = new Notification();
    String data = "";
    String priority = "high";
    boolean direct_boot_ok = true;
    List<String> registration_ids;

    public FireBaseMessage(PushNotificationDto dto) {
        notification.setTitle(dto.getTitle());
        notification.setBody(dto.getBody());
        registration_ids = dto.getPhones().stream().map(PhoneDto::getToken).collect(Collectors.toList());
    }
}
