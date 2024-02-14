package ru.spiiran.us_complex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;

public interface StatusGeneralRepository extends JpaRepository<generalStatusEntity, Long> {

}
