package ru.spiiran.us_complex.services.modelsat;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.dto.modelsat.old.equipment.dtoEquipment;
import ru.spiiran.us_complex.model.dto.modelsat.old.power.dtoMode;
import ru.spiiran.us_complex.model.dto.modelsat.old.power.dtoPowerTable;
import ru.spiiran.us_complex.repositories.modelsat.ModelSatRepository;
import ru.spiiran.us_complex.repositories.modelsat.equipment.msEquipmentRepository;
import ru.spiiran.us_complex.repositories.modelsat.power.msModeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PowerService {
    @Autowired
    msModeRepository modeRepository;
    @Autowired
    msEquipmentRepository equipmentRepository;
    @Autowired
    ModelSatRepository modelSatRepository;
    
    public dtoPowerTable getAllDataForTable() {
        List<dtoEquipment> equipmentList = equipmentRepository
                .findAll()
                .stream()
                .map(dtoEquipment::new)
                .collect(Collectors.toList());
        List<dtoMode> modeList = modeRepository
                .findAll()
                .stream()
                .map(dtoMode::new)
                .toList();
        List<dtoModelSat> modelSatList = modelSatRepository
                .findAll()
                .stream()
                .map(dtoModelSat::new)
                .toList();
        dtoPowerTable dtoPowerTable = new dtoPowerTable();
        dtoPowerTable.setDtoEquipmentList(equipmentList);
        //dtoPowerTable.setModelSat(modelSat);
        dtoPowerTable.setDtoMode(modeList);
        return dtoPowerTable;
    }
}
