package ru.spiiran.us_complex.services.connect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoEarthSat;
import ru.spiiran.us_complex.model.entitys.modelling.EarthSatEntity;
import ru.spiiran.us_complex.repositories.modelling.EarthSatRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModellingDatabaseService {

    @Autowired
    private EarthSatRepository earthSatRepository;

    public void saveResultEarthSat(List<dtoEarthSat> dtoEarthSatList) {
        earthSatRepository.saveAll(
                dtoEarthSatList
                        .stream()
                        .map(EarthSatEntity::new)
                        .collect(Collectors.toList())
        );
    }

    public void saveResultConstellationOrder(List<dtoEarthSat> dtoAssessmentConstellations) {

    }
}
