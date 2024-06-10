package ru.spiiran.us_complex.utils;

import ru.spiiran.us_complex.model.dto.modelling.response.dtoAssessmentConstellation;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoEarthSat;

import java.util.ArrayList;
import java.util.List;

public class ConverterDTO {
    public static List<dtoAssessmentConstellation> convertToAssessmentConstellationOrder(List<String> resultJSON) {
        List<dtoAssessmentConstellation> viewWindows = new ArrayList<>();
        return viewWindows;
    }

    public static List<dtoEarthSat> convertToEarthSat(List<String> modellingData){
        return null;
    }

}
