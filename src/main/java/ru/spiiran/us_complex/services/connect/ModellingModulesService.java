package ru.spiiran.us_complex.services.connect;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.IDTOSMAOResponse;
import ru.spiiran.us_complex.utils.ConverterDTO;
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

    public dtoMessage assessmentEarthSat(dtoConstellation constellation){
        // Генерирует файлы ИД для отправки их в Pro42
        // Копирует файлы в рабочую директорию движка
        List<String> modellingData;
        try {

            connectToService.genericPro42Files(constellation, ModellingType.AssessmentSatEarth);
            modellingData = connectToService.copyResponsePro42BallisticModelling();

            modellingDatabaseService.saveResultEarthSat(ConverterDTO.convertToEarthSat(modellingData));

            return new dtoMessage("SUCCESS", "Modelling has been completed");
        } catch (InterruptedException | IOException e) {
            return new dtoMessage("ERROR", "Modelling has not been completed. Description:\n" + e.getMessage());
        }

    }

    public dtoMessage assessmentConstellation(dtoConstellation constellation){
        List<String> modellingData;
        try {
            connectToService.genericPro42Files(constellation, ModellingModulesService.ModellingType.AssessmentConstructionConstellation);
            modellingData = connectToService.copyResponsePro42Modelling();
            modellingDatabaseService.saveResultConstellationOrder(
                    ConverterDTO.convertToEarthSat(modellingData)
            );
            return new dtoMessage("SUCCESS", "Modelling has been completed");
        } catch (InterruptedException | IOException e) {
            return new dtoMessage("ERROR", "Modelling has not been completed. Description:\n" + e.getMessage());
        }
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
            List<String> smaoModellingData = connectToService.copyResponseSMAOModelling();

            saveSMAOData(smaoModellingData);

            return smaoModellingData
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


    private void saveSMAOData(List<String> smaoModellingData) {

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
