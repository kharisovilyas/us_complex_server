package ru.spiiran.us_complex.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.text.StringEscapeUtils;
import ru.spiiran.us_complex.model.dto.modelling.dto_pro42.FlightData;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoAssessmentConstellation;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.IDTOSMAOResponse;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.dtoSmaoFlight;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.dtoSmaoOrder;

import java.util.ArrayList;
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

    public static List<String> splitJsonObjects(String json) {
        List<String> jsonObjects = new ArrayList<>();
        int braceCount = 0;
        int start = 0;
        boolean inString = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"') {
                inString = !inString;
            }
            if (!inString) {
                if (c == '{') {
                    if (braceCount == 0) {
                        start = i;
                    }
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        jsonObjects.add(json.substring(start, i + 1));
                    }
                }
            }
        }
        return jsonObjects;
    }

    public static IDTOSMAOResponse parseJsonToDto(String jsonResponse) {
        try {
            if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
                throw new IllegalArgumentException("Empty or null JSON response");
            }

            // Обработка JSON-строки перед разбором
            String unescapedJSON = ParserJSON.removeQuotesAndUnescape(jsonResponse);

            JsonElement jsonElement = JsonParser.parseString(unescapedJSON);
            if (!jsonElement.isJsonObject()) {
                throw new IllegalStateException("Not a JSON Object: " + unescapedJSON);
            }

            String type = jsonElement.getAsJsonObject().get("type").getAsString();
            if ("E42".equals(type)) {
                return new dtoSmaoFlight(unescapedJSON);
            } else if ("E20".equals(type)) {
                return new dtoSmaoOrder(unescapedJSON);
            } else {
                throw new IllegalArgumentException("Unknown type: " + type);
            }
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Failed to parse JSON: " + jsonResponse, e);
        }
    }

    public static String cleanJsonFromResponse(String smaoModellingData) {
        StringBuilder jsonBuilder = new StringBuilder();
        int braceCount = 0;
        boolean inString = false;
        boolean insideJson = false;

        for (char c : smaoModellingData.toCharArray()) {
            if (c == '"') {
                inString = !inString;
            }
            if (!inString) {
                if (c == '{') {
                    if (braceCount == 0) {
                        insideJson = true;
                    }
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        insideJson = false;
                        jsonBuilder.append(c);
                        break;
                    }
                }
            }
            if (insideJson) {
                jsonBuilder.append(c);
            }
        }

        return jsonBuilder.toString();
    }
}
