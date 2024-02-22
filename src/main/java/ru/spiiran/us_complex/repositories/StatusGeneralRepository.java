package ru.spiiran.us_complex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;

@Repository
public interface StatusGeneralRepository extends JpaRepository<generalStatusEntity, Long> {
    @Query("SELECT s FROM generalStatusEntity s WHERE s.statusId = 1")
    generalStatusEntity findSingleStatus();
}
