package ru.spiiran.us_complex.utils;

import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoEarthSat;

import java.util.List;
import java.util.stream.Collectors;

public class ConverterDTO {
    public static List<dtoEarthSat> convertToEarthSat(List<String> modellingData){
        return modellingData.stream().map(dtoEarthSat::new).collect(Collectors.toList());
    }
}
