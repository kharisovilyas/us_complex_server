package ru.spiiran.us_complex.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoArbitraryConstruction;
import ru.spiiran.us_complex.model.dto.constellation.dtoPlanarConstruction;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.constellation.coPlanarConstruction;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;
import ru.spiiran.us_complex.repositories.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.ConstellationRepository;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;

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
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private IdNodeRepository idNodeRepository;
    @Autowired
    private StatusGeneralRepository statusGeneralRepository;

    public List<dtoConstellation> getAllConstellations() {
        List<ConstellationEntity> entities = constellationRepository.findAllWithArbitraryList();
        return entities.stream()
                .map(dtoConstellation::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public dtoMessage addConstellation(dtoConstellation dtoConstellation) {
        try {
            generalStatusEntity generalStatus = //создание пустой записи в таблицу general Status
                    createNewGeneralStatus();
            ConstellationEntity constellation = //создание пустой записи Constellation,
                    // вызов конструктора, для инициализации некоторых полей из DTO
                    new ConstellationEntity(dtoConstellation);
            //Инициализация поля general Status в записи Constellation
            constellation.setGeneralStatus(generalStatus);
            statusGeneralRepository.save(generalStatus);
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

    public dtoMessage updateConstellation(dtoConstellation dtoConstellation, Long id) {
        try {
            Optional<ConstellationEntity> optionalConstellation = //нахождение записи с этим id,
                    // вызов метода репозитория
                    constellationRepository.findById(id);
            //Проверка на данные, которые пришли
            if(optionalConstellation.isPresent()){
                ConstellationEntity constellation = optionalConstellation.get();
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

    private void updateConstellationArbitrary(List<dtoArbitraryConstruction> dtoArbitraryConstructionList) {
        // Цикл по списку из DTO
        for (dtoArbitraryConstruction dtoArbitraryConstruction : dtoArbitraryConstructionList) {
            Long id = dtoArbitraryConstruction.getID();
            updateExistingConstellationArbitrary(dtoArbitraryConstruction, id);
        }
    }

    public List<coArbitraryConstruction> getConstellationArbitraryList(List<dtoArbitraryConstruction> dtoConstellationArbitraries) {
        List<coArbitraryConstruction> coArbitraryConstructionList = new ArrayList<>();
        for (dtoArbitraryConstruction dto : dtoConstellationArbitraries) {
            coArbitraryConstruction coArbitraryConstruction = new coArbitraryConstruction(dto);
            coArbitraryConstructionList.add(coArbitraryConstruction);
        }
        return coArbitraryConstructionList;
    }

    private List<coPlanarConstruction> getPlanarConstructionList(List<dtoPlanarConstruction> constellationOverviewList) {
        return null;
    }

    private void updateExistingConstellationArbitrary(dtoArbitraryConstruction detailedConstellation, Long id) {
        Optional<coArbitraryConstruction> optionalConstellationArbitrary = constellationArbitraryRepository.findById(id);
        if (optionalConstellationArbitrary.isPresent()) {
            coArbitraryConstruction existingConstellation = optionalConstellationArbitrary.get();
            Boolean isDeleted = detailedConstellation.getDeleted();
            if (isDeleted != null && isDeleted) {
                deleteConstellationArbitrary(existingConstellation);
            } else {
                coArbitraryConstruction updateConstellation = getUpdatedConstellationArbitrary(
                        detailedConstellation, existingConstellation
                );
                constellationArbitraryRepository.save(updateConstellation);
            }
        } else {
            throw new EntityNotFoundException("Arbitrary Construction Constellation with id " + id + " not found");
        }
    }

    private coArbitraryConstruction getUpdatedConstellationArbitrary(dtoArbitraryConstruction dtoArbitraryConstruction, coArbitraryConstruction existingConstellation) {
        existingConstellation.setAltitude(dtoArbitraryConstruction.getAltitude());
        existingConstellation.setEccentricity(dtoArbitraryConstruction.getEccentricity());
        existingConstellation.setIncline(dtoArbitraryConstruction.getIncline());
        existingConstellation.setTrueAnomaly(dtoArbitraryConstruction.getTrueAnomaly());
        existingConstellation.setLongitudeAscendingNode(dtoArbitraryConstruction.getLongitudeAscendingNode());
        existingConstellation.setPerigeeWidthArgument(dtoArbitraryConstruction.getPerigeeWidthArgument());
        return existingConstellation;
    }

    private void deleteConstellationArbitrary(coArbitraryConstruction coArbitraryConstruction) {
        idNodeRepository.delete(coArbitraryConstruction.getGeneralIdNodeEntity());
        constellationArbitraryRepository.delete(coArbitraryConstruction);
    }

    private void saveNewConstellationArbitrary(dtoArbitraryConstruction detailedConstellation, ConstellationEntity constellation) {
        generalIdNodeEntity generalIdNodeEntity = // метод для создания пустой записи general idNode
                createNewGeneralIdNodeEntity();
        coArbitraryConstruction coArbitraryConstruction = //метод для создания записи Constellation Detailed
                // с инициализацией полей general idNode и полей из DTO
                createNewConstellationArbitrary(detailedConstellation, generalIdNodeEntity, constellation);
        //сохранение в базу данных записей
        saveConstellationAndNode(
                coArbitraryConstruction, generalIdNodeEntity, constellation
        );
    }

    private void saveConstellationAndNode(
            coArbitraryConstruction coArbitraryConstruction,
            generalIdNodeEntity generalIdNodeEntity,
            ConstellationEntity constellation
    ) {
        idNodeRepository.save(generalIdNodeEntity);
        constellationRepository.save(constellation);
        constellationArbitraryRepository.save(coArbitraryConstruction);
    }

    private coArbitraryConstruction createNewConstellationArbitrary(
            dtoArbitraryConstruction detailedConstellation,
            generalIdNodeEntity generalIdNodeEntity,
            ConstellationEntity constellation
    ) {
        coArbitraryConstruction coArbitraryConstruction = new coArbitraryConstruction(detailedConstellation);
        coArbitraryConstruction.setGeneralIdNodeEntity(generalIdNodeEntity);
        coArbitraryConstruction.setConstellation(constellation);
        return coArbitraryConstruction;
    }

    public generalStatusEntity createNewGeneralStatus() {
        generalStatusEntity generalStatus = new generalStatusEntity();
        generalStatus.setStatusOfEditEarth(false);
        generalStatus.setStatusOfEditConstellation(false);
        return generalStatus;
    }

    private generalIdNodeEntity createNewGeneralIdNodeEntity() {
        generalIdNodeEntity generalIdNodeEntity = new generalIdNodeEntity();
        Long maxIdNode = findMaxIdNode();
        generalIdNodeEntity.setIdNode(maxIdNode + 1);
        return generalIdNodeEntity;
    }

    public Long findMaxIdNode() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(e.idNode) FROM generalIdNodeEntity e", Long.class);
        return query.getSingleResult() != null ? query.getSingleResult() : 0L;
    }

    @Transactional
    public dtoMessage setTableStatusOfEdit(Boolean status) {
        generalStatusEntity statusEntity = statusGeneralRepository.findSingleStatus();
        if (statusEntity != null) {
            statusEntity.setStatusOfEditConstellation(status);
            statusGeneralRepository.save(statusEntity);
            return new dtoMessage("SUCCESS TO ADD STATUS", "Table status updated successfully");
        } else {
            // Обработка случая, если запись статуса не найдена
            return new dtoMessage("ERROR TO ADD STATUS", "Status entity not found");
        }
    }

    public dtoMessage deleteConstellation(Long id) {
        return null;
    }
}
