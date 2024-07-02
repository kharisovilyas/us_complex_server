package ru.spiiran.us_complex.utils.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.dto.system.dtoSystem;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.modelsat.ModelSatRepository;
import ru.spiiran.us_complex.repositories.modelsat.OperatingParametersRepository;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;

import java.nio.file.Paths;
import java.util.List;

@Component
public class DataLoaderComponent implements CommandLineRunner {
    @Autowired private SystemRepository systemRepository;
    @Autowired private ModelSatRepository modelSatRepository;
    @Autowired private OperatingParametersRepository operatingParametersRepository;
    private final String DIRECTORY = "src/initial/modelsat";

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // Загрузка данных из файлов JSON

        List<dtoModelSat> dtoModelSatList = objectMapper.readValue(
                Paths.get(DIRECTORY,"ModelSat.json").toFile(),
                new TypeReference<>() {}
        );

        List<ModelSatEntity> modelSatEntityList = dtoModelSatList
                .stream()
                .map(it -> new ModelSatEntity(it, operatingParametersRepository))
                .toList();

        dtoSystem dtoSystem = objectMapper.readValue(
                Paths.get(DIRECTORY,"../general/System.json").toFile(),
                new TypeReference<>() {}
        );

        // Сохранение данных в базу данных
        SystemEntity systemEntity = new SystemEntity(dtoSystem);
        systemEntity.setID(1L);

        systemRepository.save(systemEntity);
        modelSatRepository.saveAllAndFlush(modelSatEntityList);
    }
}
