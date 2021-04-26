package ru.stm.lo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lo4.model.PhoneEntity;

@Repository
@Transactional
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {
    Integer deleteByToken(String token);
}
