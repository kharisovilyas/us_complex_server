package ru.spiiran.us_complex.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.spiiran.us_complex.model.dto.modelsat.equipment.dtoEquipmentType;
import ru.spiiran.us_complex.model.dto.modelsat.power.dtoMode;
import ru.spiiran.us_complex.model.dto.modelsat.techparam.dtoParameter;
import ru.spiiran.us_complex.model.dto.system.dtoSystem;
import ru.spiiran.us_complex.model.entitys.modelsat.equipment.msEquipmentTypeEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.power.msModeEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.techparam.msParameterEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.modelsat.equipment.msEquipmentTypeRepository;
import ru.spiiran.us_complex.repositories.modelsat.power.msModeRepository;
import ru.spiiran.us_complex.repositories.modelsat.techparam.msListParamRepository;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataLoaderComponent implements CommandLineRunner {
    @Autowired private msEquipmentTypeRepository equipmentTypeRepository;
    @Autowired private msListParamRepository listParamRepository;
    @Autowired private SystemRepository systemRepository;
    @Autowired private msModeRepository modeRepository;
    private final String DIRECTORY = "src/initial/modelsat";

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // Загрузка данных из файлов JSON
        List<dtoEquipmentType> dtoEquipmentTypeList = objectMapper.readValue(
            Paths.get(DIRECTORY, "EquipmentType.json").toFile(),
                new TypeReference<>() {}
        );

        List<msEquipmentTypeEntity> equipmentTypeList = dtoEquipmentTypeList
                .stream()
                .map(msEquipmentTypeEntity::new) // Преобразование каждого элемента типа Arbitrary Construction в dtoArbitraryConstruction
                .collect(Collectors.toList());

        List<dtoMode> dtoModesList = objectMapper.readValue(
                Paths.get(DIRECTORY,"Mode.json").toFile(),
                new TypeReference<>() {}
        );

        List<msModeEntity> modeList = dtoModesList
                .stream()
                .map(msModeEntity::new) // Преобразование каждого элемента типа Arbitrary Construction в dtoArbitraryConstruction
                .collect(Collectors.toList());

        List<dtoParameter> dtoParameterList = objectMapper.readValue(
                Paths.get(DIRECTORY,"ListOfParam.json").toFile(),
                new TypeReference<>() {}
        );

        List<msParameterEntity> parameterList = dtoParameterList
                .stream()
                .map(msParameterEntity::new) // Преобразование каждого элемента типа Arbitrary Construction в dtoArbitraryConstruction
                .collect(Collectors.toList());

        dtoSystem dtoSystem = objectMapper.readValue(
                Paths.get(DIRECTORY,"../general/System.json").toFile(),
                new TypeReference<>() {}
        );

        // Сохранение данных в базу данных
        SystemEntity systemEntity = new SystemEntity(dtoSystem);
        systemEntity.setID(1L);

        systemRepository.save(systemEntity);
        equipmentTypeRepository.saveAll(equipmentTypeList);
        modeRepository.saveAll(modeList);
        listParamRepository.saveAll(parameterList);
    }
}
