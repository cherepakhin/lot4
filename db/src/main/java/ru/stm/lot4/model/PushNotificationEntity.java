package ru.stm.lot4.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "push_notification")
public class PushNotificationEntity {
    @Id
    String id;
    String title = "";
    String body = "";
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pushNotifications")
    Set<PhoneEntity> phones = new HashSet<>();
    Date date;
    PushNotificationStatusEnum status;
}
