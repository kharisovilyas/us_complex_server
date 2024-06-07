package ru.spiiran.us_complex.utils;

import com.google.gson.Gson;
import org.apache.commons.text.StringEscapeUtils;
import ru.spiiran.us_complex.model.dto.modelling.dto_pro42.FlightData;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoAssessmentConstellation;

import java.util.List;

public class ParserJSON {

    public static FlightData parseFlightDataJSON(String resultJSON) {
        // Удаляем кавычки из начала и конца строки и убираем экранирование символов
        String unescapedJSON = removeQuotesAndUnescape(resultJSON);

        // Используем Gson для преобразования JSON-строки в объект FlightData
        Gson gson = new Gson();

        return gson.fromJson(unescapedJSON, FlightData.class);
    }

    public static dtoAssessmentConstellation parseViewWindowJSON(String resultJSON) {
        // Удаляем кавычки из начала и конца строки и убираем экранирование символов
        String unescapedJSON = removeQuotesAndUnescape(resultJSON);

        // Используем Gson для преобразования JSON-строки в объект FlightData
        Gson gson = new Gson();

        return gson.fromJson(unescapedJSON, dtoAssessmentConstellation.class);
    }

    public static String removeQuotesAndUnescape(String uncleanJson) {
        // Удаляем кавычки из начала и конца строки
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        // Убираем экранирование символов
        return StringEscapeUtils.unescapeJson(noQuotes);
    }

    public static List<String> getJsonList(List<String> smaoModellingData) {
        return null;
    }
}
