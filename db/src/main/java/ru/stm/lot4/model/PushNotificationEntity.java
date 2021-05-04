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
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "push_notification_phone",
            joinColumns = @JoinColumn(name = "push_notification_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    Set<PhoneEntity> phones = new HashSet<>();
    Date date;
    PushNotificationStatusEnum status;

    public PushNotificationEntity(String id,
                                  String title,
                                  String body,
                                  Date date,
                                  PushNotificationStatusEnum status) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.status = status;
    }
}
