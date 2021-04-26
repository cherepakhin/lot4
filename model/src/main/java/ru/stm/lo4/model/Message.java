package ru.stm.lo4.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Message {
    @Id
    Long id;
    String title = "";
    String body = "";
    @OneToMany
    Set<Phone> phones = new HashSet<>();
}
