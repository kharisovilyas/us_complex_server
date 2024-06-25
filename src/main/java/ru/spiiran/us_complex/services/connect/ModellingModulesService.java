package ru.spiiran.us_complex.services.connect;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelling.request.dtoViewWindowRequest;
import ru.spiiran.us_complex.model.dto.modelling.response.smao.IDTOSMAOResponse;
import ru.spiiran.us_complex.repositories.constellation.SatelliteRepository;
import ru.spiiran.us_complex.utils.converters.ConverterDTO;
import ru.spiiran.us_complex.utils.files.ParserJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModellingModulesService {

    @Autowired private ConnectToService connectToService;
    @Autowired private SatelliteRepository satelliteRepository;

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

    public dtoMessage assessmentEarthSatModelling(){
        // Генерирует файлы ИД для отправки их в Pro42
        // Копирует файлы в рабочую директорию движка
        List<String> modellingData;
        try {

            connectToService.genericPro42Files(null, ModellingType.AssessmentSatEarth);
            modellingData = connectToService.copyResponsePro42Modelling();

            modellingDatabaseService.saveResultEarthSat(ConverterDTO.convertToEarthSat(modellingData));

            return new dtoMessage("SUCCESS", "Modelling has been completed");
        } catch (InterruptedException | IOException e) {
            return new dtoMessage("ERROR", "Modelling has not been completed. Description:\n" + e.getMessage());
        }

    }

    public List<IDTOEntity> assessmentConstellation(dtoViewWindowRequest viewWindow){
        List<String> modellingData;
        try {
            connectToService.genericPro42Files(
                    viewWindow,
                    ModellingModulesService.ModellingType.AssessmentConstructionConstellation
            );
            modellingData = connectToService.copyResponsePro42Modelling();
            /*modellingDatabaseService.saveResultConstellationOrder(
                    ConverterDTO.convertToEarthSat(modellingData)
            );*/
            return ConverterDTO.convertToAssessment(modellingData, satelliteRepository);
        } catch (InterruptedException | IOException e) {
            return List.of(new dtoMessage("ERROR", "Modelling have not completed"));
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

    public dtoMessage modellingConstellationSatEarth() {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            connectToService.genericPro42Files(null, ModellingType.ConstellationSatEarth);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = connectToService.copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            connectToService.genericJsonSMAOFile(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Earth-Satellite modelling data save to database");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }

    public dtoMessage modellingConstellationNetwork(dtoViewWindowRequest viewWindow) {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            connectToService.genericPro42Files(viewWindow, ModellingType.ConstellationNetwork);

            // Генерирует файлы ИД для отправки их в Pro42
            List<String> ballisticModellingData = connectToService.copyResponsePro42BallisticModelling();

            // Генерирует файлы ИД для СМАО
            connectToService.genericJsonSMAOFile(ballisticModellingData);

            return new dtoMessage("SUCCESS", "Connect success");

        } catch (InterruptedException | JSONException | IOException e) {
            return new dtoMessage("ERROR", e.getMessage());
        }
    }

    public dtoMessage modellingConstellationDelivery(dtoViewWindowRequest viewWindow) {
        try {
            // Генерирует файлы ИД для отправки их в Pro42
            // Копирует файлы в рабочую директорию движка
            connectToService.genericPro42Files(viewWindow, ModellingType.ConstellationDelivery);

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
