package ru.stm.lot4.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import java.io.Serializable;

import static ru.stm.lot4.constants.QueryConstants.SELECT_APPLICATION_STATISTIC;

@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mobile_application")
@NamedNativeQuery(name ="MobileApplicationEntity.getStatistic", query = SELECT_APPLICATION_STATISTIC,
        resultSetMapping = "MobileApplicationStatistic")
@SqlResultSetMapping(name="MobileApplicationStatistic",
        classes={
                @ConstructorResult(targetClass = MobileApplicationStatisticEntity.class, columns={
                        @ColumnResult(name="appVersion", type=String.class),
                        @ColumnResult(name="registrationsCount", type=Long.class),
                        @ColumnResult(name="phoneNumbersCount", type=Long.class),
                })
        }
)
public class MobileApplicationEntity  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String version;
}
