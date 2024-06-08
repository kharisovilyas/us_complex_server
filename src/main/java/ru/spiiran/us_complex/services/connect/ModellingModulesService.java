package ru.spiiran.us_complex.services.connect;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoAssessmentConstellation;
import ru.spiiran.us_complex.model.dto.modelling.response.dtoEarthSat;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.IDTOSMAOResponse;
import ru.spiiran.us_complex.utils.ParserJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModellingModulesService {

    @Autowired ConnectToService connectToService;

    @Autowired
    private ModellingDatabaseService modellingDatabaseService;

    public ModellingModulesService() {}

    public enum ModellingType {
        OneSatellite,
        ConstellationSatEarth,
        ConstellationNetwork,
        ConstellationDelivery,
        AssessmentConstructionConstellation,
        AssessmentSatEarth
    }

    public List<String> assessmentEarthSat(dtoConstellation constellation){
        // Генерирует файлы ИД для отправки их в Pro42
        // Копирует файлы в рабочую директорию движка
        List<String> modellingData;
        try {

            connectToService.genericPro42Files(constellation, ModellingType.AssessmentSatEarth);
            modellingData = connectToService.copyResponsePro42BallisticModelling();

            modellingDatabaseService.saveResultEarthSat(convertToEarthSat(modellingData));

            return modellingData; /*new dtoMessage("SUCCESS", "Modelling has been completed");*/
        } catch (InterruptedException | IOException e) {
            return new ArrayList<>(); /*new dtoMessage("ERROR", "Modelling has not been completed. Description:\n" + e.getMessage());*/
        }

    }

    private List<dtoEarthSat> convertToEarthSat(List<String> ballisticModellingData) {
        return null;
    }

    public List<String> assessmentConstellation(dtoConstellation constellation){
        List<String> modellingData;
        try {
            connectToService.genericPro42Files(constellation, ModellingType.AssessmentConstructionConstellation);
            modellingData = connectToService.copyResponsePro42BallisticModelling();
            modellingDatabaseService.saveResultConstellationOrder(
                    convertToAssessmentConstellationOrder(modellingData)
            );
            return modellingData; /*new dtoMessage("SUCCESS", "Modelling has been completed");*/
        } catch (InterruptedException | IOException e) {
            return new ArrayList<>(); /*new dtoMessage("ERROR", "Modelling has not been completed. Description:\n" + e.getMessage());*/
        }
    }

    private List<dtoAssessmentConstellation> convertToAssessmentConstellationOrder(List<String> resultJSON) {
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

    public List<IDTOSMAOResponse> modellingOneSat() {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирование файлы в рабочую директорию движка
            connectToService.genericPro42Files(null, ModellingType.OneSatellite);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = connectToService.copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            connectToService.genericJsonSMAOFile(ballisticModellingData);

            // Создание файла в директории СМАО
            String smaoModellingData = connectToService.copyResponseSMAOModelling();

            saveSMAOData(smaoModellingData);

            smaoModellingData = ParserJSON.cleanJsonFromResponse(smaoModellingData);

            List<String> listOfModellingData = ParserJSON.splitJsonObjects(smaoModellingData);

            return listOfModellingData
                            .stream()
                            .map(
                                    ParserJSON::parseJsonToDto
                            )
                            .collect(
                                    Collectors.toList()
                            );

        } catch (InterruptedException | JSONException | IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private void saveSMAOData(String smaoModellingData) {

    }

    public dtoMessage modellingConstellationSatEarth(dtoConstellation constellation) {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            connectToService.genericPro42Files(constellation, ModellingType.ConstellationSatEarth);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = connectToService.copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            connectToService.genericJsonSMAOFile(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }

    public dtoMessage modellingConstellationNetwork(dtoConstellation constellation) {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            connectToService.genericPro42Files(constellation, ModellingType.ConstellationNetwork);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = connectToService.copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            connectToService.genericJsonSMAOFile(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }

    public dtoMessage modellingConstellationDelivery(dtoConstellation constellation) {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            connectToService.genericPro42Files(constellation, ModellingType.ConstellationDelivery);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = connectToService.copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            connectToService.genericJsonSMAOFile(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }
}
