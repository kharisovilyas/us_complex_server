package ru.spiiran.us_complex.services.constellation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoPlanarConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoPlanarPrm;
import ru.spiiran.us_complex.model.dto.constellation.dtoSatellite;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.repositories.NodeIdRepository;
import ru.spiiran.us_complex.repositories.constellation.ConstellationRepository;
import ru.spiiran.us_complex.repositories.constellation.SatelliteRepository;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConstellationService {
    @Autowired
    private ConstellationRepository constellationRepository;
    @Autowired
    private NodeIdRepository nodeRepository;
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private SatelliteRepository satelliteRepository;

    @Transactional
    public List<dtoConstellation> getAllConstellations() {
        List<ConstellationEntity> entities = constellationRepository.findAll();
        return entities.stream()
                .map(dtoConstellation::new)
                .collect(Collectors.toList());
    }

/*
    @Transactional
    public dtoMessage addConstellation(dtoConstellation dtoConstellation) {
        try {
            ConstellationEntity constellation = //создание пустой записи Constellation,
                    // вызов конструктора, для инициализации некоторых полей из DTO
                    new ConstellationEntity(dtoConstellation);
            //Проверка на данные, которые пришли
            if (dtoConstellation.getArbitraryFormation()) { // если пришли данные для создания записи в Constellation Detailed
                List<coArbitraryConstruction> coArbitraryConstructionList = //создание списка Constellation Detailed из списка DTO
                        getConstellationArbitraryList(dtoConstellation.getArbitraryConstructions());
                constellation //инициализация списка в сущности Constellation
                        .setArbitraryConstructionList(coArbitraryConstructionList);
                /*
                    добавление, сохранение, инициализация некоторых полей (idNode и generalStatus) списка сущностей Constellation Detailed
                    а так же добавление и сохранения поля Constellation происходит в этом методе

                addConstellationArbitrary(dtoConstellation.getArbitraryConstructions(), constellation);
                return new dtoMessage("INSERT SUCCESS", "Arbitrary Construction Constellation added successfully");

            } else { // если пришли данные для создания записи в Planar Construction
                List<coPlanarConstruction> coPlanarConstructionList = getPlanarConstructionList(dtoConstellation.getPlanarConstructions());
                constellation.setPlanarConstructionList(coPlanarConstructionList);
                addPlanarConstruction(dtoConstellation.getPlanarConstructions());
                return new dtoMessage("INSERT SUCCESS", "Planar Construction Constellation added successfully");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return new dtoMessage("INSERT ERROR", "Failed to add Constellation");
        }
    }

    @Transactional
    public dtoMessage updateConstellation(dtoConstellation dtoConstellation, Long id) {
        try {
            Optional<ConstellationEntity> optionalConstellation = //нахождение записи с этим id,
                    // вызов метода репозитория
                    constellationRepository.findById(id);
            //Проверка на данные, которые пришли
            if (optionalConstellation.isPresent()) {
                ConstellationEntity existingConstellation = optionalConstellation.get();
                existingConstellation
                        .setConstellationName(
                                dtoConstellation.getConstellationName()
                        );
                if (dtoConstellation.getArbitraryFormation()) { // если пришли данные для создания записи в Arbitrary Construction
                    existingConstellation
                            .setArbitraryConstructionList(
                                    dtoConstellation
                                            .getArbitraryConstructions()
                                            .stream()
                                            .map(coArbitraryConstruction::new)
                                            .collect(Collectors.toList())
                            );
                    updateConstellationArbitrary(dtoConstellation.getArbitraryConstructions(), existingConstellation);
                /*
                    добавление, сохранение, инициализация idNode для списка сущностей Constellation Detailed
                    а так же добавление и сохранения поля Constellation происходит в этом методе

                    return new dtoMessage("UPDATE SUCCESS", "Arbitrary Construction Constellation added successfully");

                } else { // если пришли данные для создания записи в Planar Construction
                    List<coPlanarConstruction> coPlanarConstructionList = getPlanarConstructionList(dtoConstellation.getPlanarConstructions());
                    existingConstellation.setPlanarConstructionList(coPlanarConstructionList);
                    addPlanarConstruction(dtoConstellation.getPlanarConstructions());
                    return new dtoMessage("UPDATE SUCCESS", "Planar Construction Constellation added successfully");
                }
            } else {
                return new dtoMessage("UPDATE ERROR", "Failed to update Constellation");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return new dtoMessage("UPDATE ERROR", "Failed to update Constellation");
        }
    }

    private void addPlanarConstruction(List<dtoPlanarConstruction> constellationOverviewList) {

    }

    //метод, взятый из логики update Earth Point - для обновления и одновременно добавления полей из списка сущностей
    private void addConstellationArbitrary(List<dtoArbitraryConstruction> dtoArbitraryConstructionList, ConstellationEntity constellation) {
        // Цикл по списку из DTO
        for (dtoArbitraryConstruction dtoArbitraryConstruction : dtoArbitraryConstructionList) {
            saveNewConstellationArbitrary(dtoArbitraryConstruction, constellation);
        }
    }

    private void updateConstellationArbitrary(List<dtoArbitraryConstruction> dtoArbitraryConstructionList, ConstellationEntity constellation) {
        // Цикл по списку из DTO
        for (dtoArbitraryConstruction dtoArbitraryConstruction : dtoArbitraryConstructionList) {
            Long id = dtoArbitraryConstruction.getID();
            if (id != null) {
                updateExistingConstellationArbitrary(dtoArbitraryConstruction, id);
            } else {
                saveNewConstellationArbitrary(dtoArbitraryConstruction, constellation);
            }
        }
    }

    public List<coArbitraryConstruction> getConstellationArbitraryList(List<dtoArbitraryConstruction> dtoArbitraryConstructionList) {
        List<coArbitraryConstruction> coArbitraryConstructionList = new ArrayList<>();
        for (dtoArbitraryConstruction dto : dtoArbitraryConstructionList) {
            coArbitraryConstruction coArbitraryConstruction = new coArbitraryConstruction(dto);
            coArbitraryConstructionList.add(coArbitraryConstruction);
        }
        return coArbitraryConstructionList;
    }

    private List<coPlanarConstruction> getPlanarConstructionList(List<dtoPlanarConstruction> planarConstructionList) {
        return null;
    }

    private void updateExistingConstellationArbitrary(dtoArbitraryConstruction arbitraryConstruction, Long id) {
        Optional<coArbitraryConstruction> optionalConstellationArbitrary = constellationArbitraryRepository.findById(id);
        if (optionalConstellationArbitrary.isPresent()) {
            coArbitraryConstruction existingConstellation = optionalConstellationArbitrary.get();
            Boolean isDeleted = arbitraryConstruction.getDeleted();
            if (isDeleted != null && isDeleted) {
                deleteConstellationArbitrary(existingConstellation);
            } else {

                coArbitraryConstruction updateConstellation =
                        new coArbitraryConstruction(existingConstellation, arbitraryConstruction);

                constellationArbitraryRepository.save(updateConstellation);
            }
        } else {
            throw new EntityNotFoundException("Arbitrary Construction Constellation with id " + id + " not found");
        }
    }

    private void deleteConstellationArbitrary(coArbitraryConstruction coArbitraryConstruction) {
        nodeRepository.delete(coArbitraryConstruction.getIdNodeEntity());
        constellationArbitraryRepository.delete(coArbitraryConstruction);
        // Обновляем nodeId у последующих записей:
        constellationArbitraryRepository
                .findAllByNodeIdGreaterThan(
                        coArbitraryConstruction.getIdNodeEntity().getNodeId()
                ).forEach(this::decrementNodeId);
    }

    private void decrementNodeId(coArbitraryConstruction coArbitraryConstruction) {
        IdNodeEntity idNodeEntity = coArbitraryConstruction.getIdNodeEntity();
        idNodeEntity.setNodeId(idNodeEntity.getNodeId() - 1);
        nodeRepository.save(idNodeEntity); // Сохраняем изменения
    }

    private void saveNewConstellationArbitrary(dtoArbitraryConstruction arbitraryConstruction, ConstellationEntity constellation) {
        // создание новой записи в таблице node
        IdNodeEntity nodeEntity = new IdNodeEntity(
                findMaxIdNode(),
                constellation.getConstellationName()
        );

        coArbitraryConstruction coArbitraryConstruction = // метод для создания записи Arbitrary Construction
                // с инициализацией полей general idNode и полей из DTO
                createNewArbitraryConstruction(arbitraryConstruction, nodeEntity, constellation);

        //сохранение в базу данных записей
        saveConstellationAndNode(
                coArbitraryConstruction, nodeEntity, constellation
        );
    }

    private void saveConstellationAndNode(
            coArbitraryConstruction coArbitraryConstruction,
            IdNodeEntity nodeEntity,
            ConstellationEntity constellation
    ) {
        // 1. Сначала сохраняем ConstellationEntity:
        constellationRepository.save(constellation);

        // 2. Теперь сохраняем IdNodeEntity:
        nodeRepository.save(nodeEntity);

        // 3. И только потом сохраняем coArbitraryConstruction:
        constellationArbitraryRepository.save(coArbitraryConstruction);
    }

    private coArbitraryConstruction createNewArbitraryConstruction(
            dtoArbitraryConstruction detailedConstellation,
            IdNodeEntity nodeEntity,
            ConstellationEntity constellation
    ) {
        coArbitraryConstruction coArbitraryConstruction = new coArbitraryConstruction(detailedConstellation);
        coArbitraryConstruction.setIdNodeEntity(nodeEntity);
        coArbitraryConstruction.setConstellation(constellation);
        return coArbitraryConstruction;
    }

    @Transactional
    public dtoMessage deleteConstellation(Long id) {
        Optional<ConstellationEntity> optionalConstellation = constellationRepository.findById(id);
        if (optionalConstellation.isPresent()) {
            ConstellationEntity constellation = optionalConstellation.get();

            if (constellation.getArbitraryFormation()) {
                constellation.getArbitraryConstructionList()
                        .forEach(
                                this::deleteConstellationArbitrary
                        );
            } else {
                constellation.getPlanarConstructionList()
                        .forEach(
                                this::deleteConstellationPlanar
                        );
            }
            constellationRepository.delete(constellation);
            return new dtoMessage("SUCCESS", "Constellation with id: " + id + " delete");
        } else {
            return new dtoMessage("ERROR", "Constellation with id: " + id + " not found");
        }
    }

    private void deleteConstellationPlanar(coPlanarConstruction coPlanarConstruction) {
    }
    public dtoMessage updateListConstellation(List<dtoConstellation> dtoConstellationList) {
        for (dtoConstellation constellation : dtoConstellationList) {
            try {
                Long id = constellation.getID();
                if (id == null) {
                    addConstellation(constellation);
                } else {
                    updateConstellation(constellation, id);
                }
            } catch (EntityNotFoundException exception) {
                addConstellation(constellation);
                return new dtoMessage("UPDATE SUCCESS", "Constellation updated with id " + constellation.getID());
            } catch (Exception exception) {
                exception.printStackTrace();
                return new dtoMessage("UPDATE ERROR", "An error occurred while updating EarthPoint: " + exception.getMessage());
            }
        }
        return new dtoMessage("UPDATE SUCCESS", "All Constellation updated successfully");
    }

    public dtoMessage setSystemParams(Boolean status) {
        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        if (optionalSystemEntity.isPresent()) {
            SystemEntity systemEntity = optionalSystemEntity.get();
            systemEntity.setConstellationStatus(status);
            systemRepository.save(systemEntity);
            return new dtoMessage("APPROVE SUCCESS", "System update");
        } else {
            SystemEntity systemEntity = new SystemEntity();
            systemEntity.setConstellationStatus(status);
            systemRepository.save(systemEntity);
            return new dtoMessage("APPROVE ERROR", "System not exist");
        }
    }

*/


    @Transactional
    public dtoMessage calculationPlanarConstellation(dtoPlanarConstellation planarConstellation) {
        ConstellationEntity newConstellation = new ConstellationEntity(planarConstellation);
        dtoPlanarPrm parameters = planarConstellation.getParametersCalculation();
        List<dtoSatellite> satellites = getDtoSatellites(planarConstellation, parameters);
        constellationRepository.save(newConstellation);
        List<SatelliteEntity> satelliteEntities = new ArrayList<>(satellites.stream()
                .map(dtoSatellite -> {
                    SatelliteEntity satellite = new SatelliteEntity(
                            planarConstellation.getConstellationName(),
                            findMaxIdNode(),
                            newConstellation,
                            dtoSatellite,
                            nodeRepository
                    );
                    return satelliteRepository.save(satellite);
                })
                .toList());
        newConstellation.setSatelliteEntities(satelliteEntities);
        return new dtoMessage("SUCCESS", "ADD");
    }

    private static List<dtoSatellite> getDtoSatellites(dtoPlanarConstellation planarConstellation, dtoPlanarPrm parameters) {
        long numberOfPlane = parameters.getNumberOfPlane();
        long numberOfPosition = parameters.getPositionPlane();
        double longitudePlane1 = parameters.getLongitudeOfPlane1();
        double spacecraftLongitude = parameters.getSpacecraftOfLongitude();
        long firstPosition = parameters.getFirstPositionInPlane1();
        double spacecraftSpacing = parameters.getSpacecraftSpacing();
        double phaseShift = parameters.getPhaseShift();
        List<dtoSatellite> satellites = new ArrayList<>();

        for(long plane = 1L; plane <= numberOfPlane; plane++){
            for(long position = 1L; position <= numberOfPosition; position++){
                dtoSatellite satellite = new dtoSatellite(planarConstellation.getParametersCalculation());
                satellite.setPlane(plane);
                satellite.setPosition(position);
                satellite.setLongitudeAscendingNode(
                        longitudePlane1 + spacecraftLongitude * (plane - 1L)
                );
                satellite.setTrueAnomaly(
                        firstPosition + spacecraftSpacing * (position - 1L) + phaseShift*(plane - 1L)
                );
                satellites.add(satellite);
            }
        }
        return satellites;
    }


    @Transactional
    public dtoMessage updateConstellation(dtoConstellation dtoConstellation) {
        try {
            if (dtoConstellation.getID() != null) {
                Optional<ConstellationEntity> optionalConstellation = constellationRepository.findById(dtoConstellation.getID());
                if (optionalConstellation.isPresent()) {
                    ConstellationEntity existingConstellation = new ConstellationEntity(dtoConstellation, optionalConstellation.get());
                    if (dtoConstellation.getArbitraryFormation()) {
                        List<SatelliteEntity> existingArbitraryConstructions = existingConstellation.getSatelliteEntities();
                        if (existingArbitraryConstructions == null) {
                            existingArbitraryConstructions = new ArrayList<>();
                        }

                        // Обновляем список satellite
                        List<SatelliteEntity> finalExistingSatellites = existingArbitraryConstructions;
                        existingConstellation.setSatelliteEntities(
                                dtoConstellation
                                        .getSatellites()
                                        .stream()
                                        .filter(dtoSatellite -> dtoSatellite.getDeleted() == null || !dtoSatellite.getDeleted())
                                        .map(dtoSatellite -> {

                                            // Ищем соответствующую запись в existingSatellites
                                            Optional<SatelliteEntity> existingSatellite = finalExistingSatellites.stream()
                                                    .filter(existing -> existing.getSatelliteId().equals(dtoSatellite.getSatelliteId()))
                                                    .findFirst();

                                            if (existingSatellite.isEmpty()) {
                                                // Получаем список SatelliteEntity, которые не принадлежат к текущей констелляции
                                                List<SatelliteEntity> satellitesToUpdate = satelliteRepository.findAllNotInConstellation(existingConstellation.getConstellationId());
                                                satellitesToUpdate
                                                        .stream()
                                                        .filter(satellite -> satellite.getConstellation().getConstellationId() > existingConstellation.getConstellationId())
                                                        .forEach(this::incrementNodeId);
                                            }

                                            // Создаем новый объект Satellite с DTO и Entity
                                            return satelliteRepository.save(
                                                    new SatelliteEntity(
                                                            existingSatellite.orElse(null),
                                                            dtoSatellite,
                                                            existingConstellation,
                                                            nodeRepository,
                                                            new IdNodeEntity(
                                                                    findMaxNodeConstellation(existingConstellation) + 1,
                                                                    dtoConstellation.getConstellationName() == null ?
                                                                            dtoConstellation.getConstellationName() : existingConstellation.getConstellationName()
                                                            )
                                                    )
                                            );
                                        })
                                        .collect(Collectors.toList())
                        );

                        // Сохраняем изменения в ConstellationEntity
                        constellationRepository.saveAndFlush(existingConstellation);

                        // Удаляем SatelliteEntity, где isDeleted = true
                        dtoConstellation
                                .getSatellites()
                                .stream()
                                .filter(dtoSatellite -> dtoSatellite.getDeleted() == null || dtoSatellite.getDeleted())
                                .filter(dtoSatellite -> dtoSatellite.getSatelliteId() != null)
                                .forEach(this::deleteSatelliteAndDecrementNodes);


                        return new dtoMessage("SUCCESS", "UPDATE Satellite");

                    } else {
                        // Сохраняем изменения в ConstellationEntity
                        constellationRepository.saveAndFlush(existingConstellation);

                        return new dtoMessage("SUCCESS", "UPDATE Planar Satellite");
                    }

                } else {
                    throw new EntityNotFoundException();
                }

            } else {
                throw new IllegalArgumentException();
            }

        } catch (EntityNotFoundException | IllegalArgumentException exception) {
            // Сохраняем Constellation Entity
            ConstellationEntity newConstellation = new ConstellationEntity(dtoConstellation);
            List<SatelliteEntity> satelliteEntities = new ArrayList<>();
            if (dtoConstellation.getArbitraryFormation() && dtoConstellation.getSatellites() != null) {
                satelliteEntities.addAll(
                        dtoConstellation.getSatellites().stream()
                                .map(dtoSatellite -> {
                                    SatelliteEntity satellite = new SatelliteEntity(
                                            dtoConstellation.getConstellationName(),
                                            findMaxIdNode(),
                                            newConstellation,
                                            dtoSatellite,
                                            nodeRepository
                                    );
                                    return satelliteRepository.save(satellite);
                                })
                                .toList()
                );
            }
            newConstellation.setSatelliteEntities(satelliteEntities);
            constellationRepository.save(newConstellation);
            return new dtoMessage("SUCCESS", "ADD");
        }
    }

    @Transactional
    private void deleteSatelliteAndDecrementNodes(dtoSatellite dtoSatellite) {
        // Получаем SatelliteEntity
        Optional<SatelliteEntity> satellite = satelliteRepository.findById(dtoSatellite.getSatelliteId());

        // Удаляем SatelliteEntity
        if (satellite.isPresent()) {
            satelliteRepository.deleteById(satellite.get().getSatelliteId());
            // Получаем idNode удаленной SatelliteEntity
            Long deletedNodeId = satellite.get().getIdNodeEntity().getNodeId();
            // Обновляем idNode у остальных SatelliteEntity
            List<SatelliteEntity> satellitesToUpdate = satelliteRepository.findAllByNodeIdGreaterThan(deletedNodeId);
            satellitesToUpdate
                    .forEach(this::decrementSatelliteNodeId);
        }
    }

    @Transactional
    public dtoMessage deleteConstellation(Long id) {
        Optional<ConstellationEntity> optionalConstellation = constellationRepository.findById(id);
        if (optionalConstellation.isPresent()) {
            ConstellationEntity constellation = optionalConstellation.get();
            try {
                constellation.getSatelliteEntities().forEach(this::deleteSatellite);
                constellationRepository.delete(constellation);
                return new dtoMessage("SUCCESS", "Constellation with id: " + id + " deleted");
            } catch (Exception e) {
                e.printStackTrace();  // Логирование исключения
                return new dtoMessage("ERROR", "Failed to delete Constellation with id: " + id + ". Reason: " + e.getMessage());
            }
        } else {
            return new dtoMessage("ERROR", "Constellation with id: " + id + " not found");
        }
    }

    @Transactional
    private void deleteSatellite(SatelliteEntity satellite) {
        // Удаляем SatelliteEntity из базы данных
        satelliteRepository.delete(satellite);

        // Удаляем IdNodeEntity, связанный с SatelliteEntity (если он есть)
        if (satellite.getIdNodeEntity() != null) {
            nodeRepository.delete(satellite.getIdNodeEntity());
        }
    }


    private Long findMaxNodeConstellation(ConstellationEntity constellation) {
        Long maxConstellationNode = nodeRepository.findMaxConstellationIdNode(constellation.getConstellationId());
        //TODO: здесь текущая проблема и зарыта
        return maxConstellationNode == null ? findMaxIdNode() : maxConstellationNode;
    }

    public Long findMaxIdNode() {
        Long maxIdNode = nodeRepository.findMaxIdNode();
        return maxIdNode == null ? 0L : maxIdNode;
    }

    @Transactional
    private void incrementNodeId(SatelliteEntity satelliteEntity) {
        IdNodeEntity idNodeEntity = satelliteEntity.getIdNodeEntity();
        idNodeEntity.setNodeId(idNodeEntity.getNodeId() + 1);
        nodeRepository.saveAndFlush(idNodeEntity);
    }

    @Transactional
    private void decrementSatelliteNodeId(SatelliteEntity satellite) {
        IdNodeEntity idNodeEntity = satellite.getIdNodeEntity();
        idNodeEntity.setNodeId(idNodeEntity.getNodeId() - 1);
        nodeRepository.save(idNodeEntity);
        satelliteRepository.saveAndFlush(satellite); // Сохраняем Satellite
    }

    public dtoMessage setSystemParams(Boolean status) {
        return null;
    }
}
