package ru.spiiran.us_complex.repositories.modelsat;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spiiran.us_complex.model.entitys.modelsat.equipment.msEquipmentTypeEntity;

public interface msEquipmentTypeRepository extends JpaRepository<msEquipmentTypeEntity, Long> {
}
