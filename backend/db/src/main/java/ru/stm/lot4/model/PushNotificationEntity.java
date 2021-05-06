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
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "push_notification_phone",
            joinColumns = @JoinColumn(name = "push_notification_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id", referencedColumnName = "id"))
    Set<PhoneEntity> phones = new HashSet<>();
    Date date;
    PushNotificationStatusEnum status;
}