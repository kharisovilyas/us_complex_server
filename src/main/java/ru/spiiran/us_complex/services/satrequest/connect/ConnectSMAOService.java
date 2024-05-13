package ru.spiiran.us_complex.services.satrequest.connect;

import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.general.dtoIdNode;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.satrequest.dtoRequest;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.constellation.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.satrequest.CatalogRepository;
import ru.spiiran.us_complex.repositories.satrequest.RequestRepository;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;
import ru.spiiran.us_complex.model.dto.satrequest.dto_smao.Event;
import ru.spiiran.us_complex.model.dto.satrequest.dto_smao.Node;
import ru.spiiran.us_complex.model.dto.satrequest.dto_smao.Parameters;
import ru.spiiran.us_complex.model.dto.satrequest.dto_smao.Satellite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConnectSMAOService {
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ConstellationArbitraryRepository constellationArbitraryRepository;
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private IdNodeRepository idNodeRepository;

    public dtoMessage startModelling(dtoRequest request) {
        try{
            Node node = createNode(request.getIdNode());
            List<Satellite> satellites =
                    constellationArbitraryRepository
                            .findAll()
                            .stream()
                            .map(this::createSatellite)
                            .collect(Collectors.toList());
            Parameters parameters = createParameters();

            Event event = new Event("E00", node, satellites, parameters, true);

            Gson gson = new Gson();
            String json = gson.toJson(event);

            // Получаем текущую директорию
            String currentDirectory = System.getProperty("user.dir");

            // Создаем путь к файлу в текущей директории
            String filePath = currentDirectory + "/event.json";

            // Создаем объект FileWriter для записи в файл
            FileWriter writer = new FileWriter(filePath);

            // Записываем JSON-строку в файл
            writer.write(json);

            // Закрываем FileWriter после использования
            writer.close();

            return new dtoMessage("SUCCESS", "Modelling Start");
        }catch (EntityNotFoundException exception){
            return new dtoMessage("ERROR", exception.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Parameters createParameters() throws EntityNotFoundException {
        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        if (optionalSystemEntity.isPresent()) {
            SystemEntity system = optionalSystemEntity.get();
            return new Parameters(
                    System.currentTimeMillis() / 1000,
                    system.getModelingBegin(),
                    system.getModelingEnd(),
                    system.getStep(),
                    system.getInterSatelliteCommunication(),
                    system.getControlSystem(),
                    system.getDuration()

            );
        } else {
            throw new EntityNotFoundException("System parameters not exist");
        }

    }

    private Satellite createSatellite(coArbitraryConstruction satellite) {
        return new Satellite(
                satellite.getID(),
                satellite.getConstellation().getID(),
                satellite.getModelSat(),
                1, //TODO:
                1, //TODO:
                satellite.getAltitude(),
                satellite.getEccentricity(),
                satellite.getIncline(),
                satellite.getLongitudeAscendingNode(),
                satellite.getPerigeeWidthArgument(),
                satellite.getTrueAnomaly(),
                satellite.getGeneralIdNodeEntity().getNodeType()
        );
    }

    private Node createNode(dtoIdNode idNode) {
        return new Node(
                Integer.parseInt(idNode.getIdNode().toString()),
                idNode.getNodeType()
        );
    }



}




