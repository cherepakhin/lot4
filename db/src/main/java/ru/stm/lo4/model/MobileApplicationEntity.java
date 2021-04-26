package ru.stm.lo4.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mobile_application")
public class MobileApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String version;
}
