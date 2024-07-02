package ru.spiiran.us_complex.services.connect;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoAssessment;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoEarthSat;
import ru.spiiran.us_complex.model.entitys.modelling.EarthSatEntity;
import ru.spiiran.us_complex.repositories.constellation.SatelliteRepository;
import ru.spiiran.us_complex.repositories.earth.EarthPointRepository;
import ru.spiiran.us_complex.repositories.modelling.EarthSatRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModellingDatabaseService {
    @Autowired private EarthPointRepository earthPointRepository;
    @Autowired private SatelliteRepository satelliteRepository;
    @Autowired private EarthSatRepository earthSatRepository;

    @Transactional
    public void saveResultEarthSat(List<dtoAssessment> dtoAssessmentList) {
        dtoAssessmentList
                        .forEach(it -> new EarthSatEntity(it, earthSatRepository, earthPointRepository, satelliteRepository));
    }

    @Transactional
    public void saveResultContactEarthSat(List<dtoEarthSat> earthSatList) {
        earthSatList
                .forEach(it -> new EarthSatEntity(it, earthSatRepository, earthPointRepository, satelliteRepository));
    }


    public void saveResultConstellationOrder(List<dtoEarthSat> dtoAssessmentConstellations) {
        //TODO: если понадобиться - то для сохранения данных по constellation
    }

    public List<dtoEarthSat> getAllAssessmentEarthSat() {
        return earthSatRepository.findAll().stream().map(dtoEarthSat::new).collect(Collectors.toList());
    }

    public List<dtoEarthSat> getEarthSatByConstellationId(Long constellationId) {
        return null;
    }
}
