package ru.stm.lo4.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class PhoneEntity {
    @Id
    Long id;
    String number;
    @ManyToOne
    App app;
}
