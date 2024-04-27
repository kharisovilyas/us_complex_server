package ru.spiiran.us_complex.services.modelsat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.equipment.dtoEquipment;
import ru.spiiran.us_complex.model.dto.modelsat.equipment.dtoEquipmentType;
import ru.spiiran.us_complex.model.entitys.modelsat.equipment.msEquipmentEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.equipment.msEquipmentTypeEntity;
import ru.spiiran.us_complex.repositories.modelsat.equipment.msEquipmentRepository;
import ru.spiiran.us_complex.repositories.modelsat.equipment.msEquipmentTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipmentService {
    @Autowired
    msEquipmentTypeRepository equipmentTypeRepository;
    @Autowired
    msEquipmentRepository equipmentRepository;
    public List<dtoEquipmentType> getAllEquipmentTypes() {
        return equipmentTypeRepository
                .findAll()
                .stream()
                .map(dtoEquipmentType::new)
                .collect(Collectors.toList());
    }

    public dtoMessage addAndUpdateEquipment(dtoEquipment dtoEquipment) {
        Optional<msEquipmentTypeEntity> optionalEquipmentTypeEntity = equipmentTypeRepository.findById(dtoEquipment.getType().getDevId());
        if(optionalEquipmentTypeEntity.isPresent()){
            Optional<msEquipmentEntity> optionalEquipmentEntity = equipmentRepository.findById(dtoEquipment.getId());
            if(optionalEquipmentEntity.isPresent()){
                msEquipmentEntity existingEquipmentEntity = optionalEquipmentEntity.get();
                existingEquipmentEntity.setDevName(dtoEquipment.getDevName());
                existingEquipmentEntity.setTypeEntity(new msEquipmentTypeEntity(dtoEquipment.getType()));
                return equipmentRepository.save(
                        existingEquipmentEntity
                ).getDtoMessage("SUCCESS", "Equipment update");
            }else {
                return equipmentRepository.save(
                        new msEquipmentEntity(
                                optionalEquipmentTypeEntity.get(),
                                dtoEquipment.getDevName()
                        )
                ).getDtoMessage("SUCCESS", "Add Equipment");
            }
        } else {
            return new dtoMessage("ERROR", "This type not exist");
        }
    }
}
