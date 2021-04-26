package ru.stm.lo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stm.lo4.model.Phone;

@Repository
public interface PhoneRepository extends JpaRepository<Phone,Long> {
}
