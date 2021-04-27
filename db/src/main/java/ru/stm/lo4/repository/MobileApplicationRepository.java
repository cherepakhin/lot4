package ru.stm.lo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.stm.lo4.model.MobileApplicationEntity;
import ru.stm.lo4.model.MobileApplicationStatisticEntity;

import java.util.List;

@Transactional
@Repository
public interface MobileApplicationRepository extends JpaRepository<MobileApplicationEntity, Long> {
    MobileApplicationEntity findByVersion(String version);
    @Query(nativeQuery = true)
    List<MobileApplicationStatisticEntity> getStatistic();
}
