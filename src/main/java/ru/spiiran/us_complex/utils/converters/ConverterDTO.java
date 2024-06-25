package ru.spiiran.us_complex.utils.converters;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoAssessment;
import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;
import ru.spiiran.us_complex.repositories.constellation.SatelliteRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConverterDTO {
    public static List<dtoAssessment> convertToEarthSat(List<String> modellingData){
        return modellingData.stream().map(dtoAssessment::new).collect(Collectors.toList());
    }

    public static List<IDTOEntity> convertToAssessment(List<String> modellingData, SatelliteRepository satelliteRepository){
        return modellingData
                .stream()
                .map(dtoAssessment::new)
                .filter(dtoAssessment -> dtoAssessment.getScLabel() != null)
                .peek(dtoAssessment ->
                                getNodeFromScLabel(
                                        dtoAssessment,
                                        satelliteRepository
                                )

                )
                .collect(Collectors.toList());
    }

    private static void getNodeFromScLabel(dtoAssessment assessment, SatelliteRepository satelliteRepository) {
        String scLabel = assessment.getScLabel();
        Optional<SatelliteEntity> satelliteEntityOptional = satelliteRepository.findById(Long.parseLong(scLabel));
        satelliteEntityOptional.ifPresent(satellite -> assessment.setScLabel(satellite.getIdNodeEntity().getNodeId().toString()));
    }
}
