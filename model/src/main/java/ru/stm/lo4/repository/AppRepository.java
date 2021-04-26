package ru.stm.lo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stm.lo4.model.App;

public interface AppRepository extends JpaRepository<App, Long> {
}
