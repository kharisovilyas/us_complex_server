package ru.spiiran.us_complex.repositories.modelsat.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.modelsat.equipment.msEquipmentTypeEntity;
@Repository
public interface msEquipmentTypeRepository extends JpaRepository<msEquipmentTypeEntity, Long> {
}
