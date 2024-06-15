package ru.spiiran.us_complex.utils.converters;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoAssessment;

import java.util.List;
import java.util.stream.Collectors;

public class ConverterDTO {
    public static List<dtoAssessment> convertToEarthSat(List<String> modellingData){
        return modellingData.stream().map(dtoAssessment::new).collect(Collectors.toList());
    }

    public static List<IDTOEntity> convertToAssessment(List<String> modellingData){
        return modellingData.stream().map(dtoAssessment::new).collect(Collectors.toList());
    }
}
