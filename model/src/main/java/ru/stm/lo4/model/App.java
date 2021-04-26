package ru.stm.lo4.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class App {
    @Id
    Long id;
    String token;
    String version;
}
