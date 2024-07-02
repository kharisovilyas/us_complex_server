package ru.spiiran.us_complex.repositories.modelsat.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.spiiran.us_complex.model.entitys.modelsat.old.equipment.msEquipmentEntity;
@Repository
public interface msEquipmentRepository extends JpaRepository<msEquipmentEntity, Long> {
}
