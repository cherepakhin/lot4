package ru.stm.lot4.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "phone")
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String number;
    String token;
    @ManyToOne
    MobileApplicationEntity app;
//    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinTable(name = "push_notification_phone",
//            joinColumns = @JoinColumn(name = "phone_id"),
//            inverseJoinColumns = @JoinColumn(name = "push_notification_id", referencedColumnName = "id"))
//    Set<PushNotificationEntity> pushNotifications = new HashSet<>();
    @Column(name = "is_active")
    Boolean isActive;
}
