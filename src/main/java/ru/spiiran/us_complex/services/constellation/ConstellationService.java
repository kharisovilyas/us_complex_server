package ru.spiiran.us_complex.services.constellation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoArbitraryConstruction;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoPlanarConstruction;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.constellation.coPlanarConstruction;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.NodeIdRepository;
import ru.spiiran.us_complex.repositories.constellation.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.constellation.ConstellationRepository;
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
    private ConstellationArbitraryRepository constellationArbitraryRepository;
    @Autowired
    private NodeIdRepository nodeRepository;
    @Autowired
    private SystemRepository systemRepository;


    public List<dtoConstellation> getAllConstellations() {
        List<ConstellationEntity> entities = constellationRepository.findAllWithArbitraryList();
        return entities.stream()
                .map(dtoConstellation::new)
                .collect(Collectors.toList());
    }


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
                 */
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
                */
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

    public Long findMaxIdNode() {
        Long maxIdNode = nodeRepository.findMaxIdNode();
        return maxIdNode == null ? 0L : maxIdNode;
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
            SystemEntity systemEntity =  new SystemEntity();
            systemEntity.setConstellationStatus(status);
            systemRepository.save(systemEntity);
            return new dtoMessage("APPROVE ERROR", "System not exist");
        }
    }
}
