package ru.spiiran.us_complex.utils.files;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.text.StringEscapeUtils;
import ru.spiiran.us_complex.model.dto.modelling.dto_pro42.FlightData;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.IDTOSMAOResponse;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.dtoSmaoFlight;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.dtoSmaoOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserJSON {

    public static FlightData parseFlightDataJSON(String resultJSON) {
        // Удаляем кавычки из начала и конца строки и убираем экранирование символов
        String unescapedJSON = removeQuotesAndUnescape(resultJSON);

        // Используем Gson для преобразования JSON-строки в объект FlightData
        Gson gson = new Gson();

        return gson.fromJson(unescapedJSON, FlightData.class);
    }

    public static String removeQuotesAndUnescape(String uncleanJson) {
        // Удаляем кавычки из начала и конца строки
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        // Убираем экранирование символов
        return StringEscapeUtils.unescapeJson(noQuotes);
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

    public static List<String> splitJsonFromResponse(String smaoModellingData) {
        List<String> jsonObjects = new ArrayList<>();
        StringBuilder currentObject = new StringBuilder();
        int braceCount = 0;
        boolean inString = false;

        for (char c : smaoModellingData.toCharArray()) {
            if (c == '"') {
                inString = !inString;
            }
            if (!inString) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                }
            }

            if (braceCount > 0) {
                currentObject.append(c);
            }

            if (braceCount == 0 && !currentObject.isEmpty()) {
                currentObject.append(c); // add the closing brace
                jsonObjects.add(currentObject.toString().trim());
                currentObject.setLength(0);
            }
        }

        return jsonObjects;
    }

    public static List<String> toListJsonFromResponse(String string) {
        List<String> jsonObjects = new ArrayList<>();

        // Регулярное выражение для поиска объектов JSON в строке
        Pattern pattern = Pattern.compile("\\{[^\\{\\}]*\\}");
        Matcher matcher = pattern.matcher(string);

        // Ищем и добавляем объекты JSON в список
        while (matcher.find()) {
            jsonObjects.add(matcher.group());
        }

        return jsonObjects;
    }
}
