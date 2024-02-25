package ru.spiiran.us_complex.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellationArbitrary;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellationPlanar;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.ConstellationArbitrary;
import ru.spiiran.us_complex.model.entitys.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.ConstellationPlanar;
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
    @Autowired private StatusGeneralRepository statusGeneralRepository;

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
                List<ConstellationArbitrary> constellationArbitraryList = //создание списка Constellation Detailed из списка DTO
                        getConstellationArbitraryList(dtoConstellation.getConstellationArbitraryList());
                constellation //инициализация списка в сущности Constellation
                        .setConstellationArbitraryList(constellationArbitraryList);
                /*
                добавление, сохранение, инициализация некоторых полей (idNode и generalStatus) списка сущностей Constellation Detailed
                а так же добавление и сохранения поля Constellation происходит в этом методе
                 */
                addConstellationArbitrary(dtoConstellation.getConstellationArbitraryList(), constellation);
                return new dtoMessage("INSERT CONSTELLATION DETAILED SUCCESS", "Constellation Detailed added successfully");

            } else { // если пришли данные для создания записи в Constellation Overview
                List<ConstellationPlanar> constellationPlanarList = getConstellationOverviewList(dtoConstellation.getConstellationPlanarList());
                constellation.setConstellationPlanarList(constellationPlanarList);
                addConstellationOverview(dtoConstellation.getConstellationPlanarList());
                return new dtoMessage("INSERT CONSTELLATION OVERVIEW SUCCESS", "Constellation Overview added successfully");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return new dtoMessage("INSERT ERROR", "Failed to add EarthPoint");
        }
    }

    private void addConstellationOverview(List<dtoConstellationPlanar> constellationOverviewList) {

    }

    //метод, взятый из логики update Earth Point - для обновления и одновременно добавления полей из списка сущностей
    private void addConstellationArbitrary(List<dtoConstellationArbitrary> dtoConstellationArbitraryList, ConstellationEntity constellation) {
        // Цикл по списку из DTO
        for (dtoConstellationArbitrary dtoConstellationArbitrary : dtoConstellationArbitraryList) {
            try {
                //Получение id записи из DTO -
                // если не null - то обновляем запись
                // если null - то добавляем запись в таблицу
                Long id = dtoConstellationArbitrary.getID();
                if (id == null) {
                    saveNewConstellationArbitrary(dtoConstellationArbitrary, constellation);
                } else {
                    updateExistingConstellationArbitrary(dtoConstellationArbitrary, id);
                }
            } catch (EntityNotFoundException exception) {
                saveNewConstellationArbitrary(dtoConstellationArbitrary, constellation);
            }
        }
    }

    public List<ConstellationArbitrary> getConstellationArbitraryList(List<dtoConstellationArbitrary> dtoConstellationArbitraries) {
        List<ConstellationArbitrary> constellationArbitraryList = new ArrayList<>();
        for (dtoConstellationArbitrary dto : dtoConstellationArbitraries) {
            ConstellationArbitrary constellationArbitrary = new ConstellationArbitrary(dto);
            constellationArbitraryList.add(constellationArbitrary);
        }
        return constellationArbitraryList;
    }

    private List<ConstellationPlanar> getConstellationOverviewList(List<dtoConstellationPlanar> constellationOverviewList) {
        return null;
    }

    public dtoMessage updateConstellation(dtoConstellation dtoConstellation, Long id) {
        return null;
    }

    private void updateExistingConstellationArbitrary(dtoConstellationArbitrary detailedConstellation, Long id) {
        Optional<ConstellationArbitrary> optionalConstellationArbitrary = constellationArbitraryRepository.findById(id);
        if (optionalConstellationArbitrary.isPresent()) {
            ConstellationArbitrary existingConstellation = optionalConstellationArbitrary.get();
            Boolean isDeleted = detailedConstellation.getDeleted();
            if (isDeleted != null && isDeleted) {
                deleteConstellationArbitrary(existingConstellation);
            } else {
                ConstellationArbitrary updateConstellation = getUpdatedConstellationArbitrary(
                        detailedConstellation, existingConstellation
                );
                constellationArbitraryRepository.save(updateConstellation);
            }
        } else {
            throw new EntityNotFoundException("Constellation Arbitrary with id " + id + " not found");
        }
    }

    private ConstellationArbitrary getUpdatedConstellationArbitrary(dtoConstellationArbitrary dtoConstellationArbitrary, ConstellationArbitrary existingConstellation) {
        existingConstellation.setAltitude(dtoConstellationArbitrary.getAltitude());
        existingConstellation.setEccentricity(dtoConstellationArbitrary.getEccentricity());
        existingConstellation.setIncline(dtoConstellationArbitrary.getIncline());
        existingConstellation.setTrueAnomaly(dtoConstellationArbitrary.getTrueAnomaly());
        existingConstellation.setLongitudeAscendingNode(dtoConstellationArbitrary.getLongitudeAscendingNode());
        existingConstellation.setPerigeeWidthArgument(dtoConstellationArbitrary.getPerigeeWidthArgument());
        return existingConstellation;
    }

    private void deleteConstellationArbitrary(ConstellationArbitrary constellationArbitrary) {
        idNodeRepository.delete(constellationArbitrary.getGeneralIdNodeEntity());
        constellationArbitraryRepository.delete(constellationArbitrary);
    }

    private void saveNewConstellationArbitrary(dtoConstellationArbitrary detailedConstellation, ConstellationEntity constellation) {
        generalIdNodeEntity generalIdNodeEntity = // метод для создания пустой записи general idNode
                createNewGeneralIdNodeEntity();
        ConstellationArbitrary constellationArbitrary = //метод для создания записи Constellation Detailed
                // с инициализацией полей general idNode и полей из DTO
                createNewConstellationArbitrary(detailedConstellation, generalIdNodeEntity, constellation);
        //сохранение в базу данных записей
        saveConstellationAndNode(
                constellationArbitrary, generalIdNodeEntity, constellation
        );
    }

    private void saveConstellationAndNode(
            ConstellationArbitrary constellationArbitrary,
            generalIdNodeEntity generalIdNodeEntity,
            ConstellationEntity constellation
    ) {
        idNodeRepository.save(generalIdNodeEntity);
        constellationRepository.save(constellation);
        constellationArbitraryRepository.save(constellationArbitrary);
    }

    private ConstellationArbitrary createNewConstellationArbitrary(
            dtoConstellationArbitrary detailedConstellation,
            generalIdNodeEntity generalIdNodeEntity,
            ConstellationEntity constellation
    ) {
        ConstellationArbitrary constellationArbitrary = new ConstellationArbitrary(detailedConstellation);
        constellationArbitrary.setGeneralIdNodeEntity(generalIdNodeEntity);
        constellationArbitrary.setConstellation(constellation);
        return constellationArbitrary;
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
}
