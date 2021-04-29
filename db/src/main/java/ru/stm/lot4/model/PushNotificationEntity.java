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
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "push_notification")
public class PushNotificationEntity {
    @Id
    String id;
    String title = "";
    String body = "";
    //    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pushNotifications")
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "push_notification_phone",
            joinColumns = @JoinColumn(name = "push_notification_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    Set<PhoneEntity> phones = new HashSet<>();
    Date date;
    PushNotificationStatusEnum status;

    public void addPhone(PhoneEntity phoneEntity) {
        this.phones.add(phoneEntity);
        phoneEntity.getPushNotifications().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PushNotificationEntity)) return false;

        PushNotificationEntity that = (PushNotificationEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
