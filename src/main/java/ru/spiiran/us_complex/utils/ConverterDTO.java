package ru.spiiran.us_complex.utils;

import ru.spiiran.us_complex.model.dto.modelling.response.dtoAssessmentConstellation;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoEarthSat;

import java.util.ArrayList;
import java.util.List;

public class ConverterDTO {
    public static List<dtoAssessmentConstellation> convertToAssessmentConstellationOrder(List<String> resultJSON) {
        List<dtoAssessmentConstellation> viewWindows = new ArrayList<>();
        for (String data : resultJSON) {
            // Удаляем "ViewVindows Array": из строки JSON
            String cleanedJSON = data
                    .replace("\"ViewVindows Array\":", "")
                    .replace("{", "")
                    .replace("[", "")
                    .replace("},", "}");

            // Парсим JSON с помощью метода из ParserJSON
            viewWindows.add(
                    ParserJSON.parseViewWindowJSON(cleanedJSON)
            );
        }
        return viewWindows;
    }

    public static List<dtoEarthSat> convertToEarthSat(List<String> modellingData) {
        return null;
    }
}
