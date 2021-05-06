package ru.stm.lot4.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lot4.model.PhoneEntity;

import java.util.List;

@Repository
@Transactional
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {
    @Modifying
    @Query("UPDATE PhoneEntity as p set p.isActive = false where p.token = :token")
    Integer deleteByToken(String token);

    @Cacheable(cacheNames = "phones", key = "#number")
    List<PhoneEntity> findByNumber(String number);

}
