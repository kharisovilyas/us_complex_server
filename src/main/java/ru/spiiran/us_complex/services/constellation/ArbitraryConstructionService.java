package ru.spiiran.us_complex.services.constellation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoArbitraryConstruction;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;
import ru.spiiran.us_complex.repositories.constellation.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.constellation.ConstellationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArbitraryConstructionService {
    @Autowired
    private ConstellationArbitraryRepository constellationArbitraryRepository;
    @Autowired
    private ConstellationRepository constellationRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IdNodeRepository idNodeRepository;
    @Autowired
    private StatusGeneralRepository statusGeneralRepository;

    public List<coArbitraryConstruction> getAllDetailedConstellations() {
        return constellationArbitraryRepository.findAll();
    }

    @Transactional
    public dtoMessage addConstellationArbitrary(Long id, dtoArbitraryConstruction detailedConstellation) {
        try {
            constellationArbitraryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arbitrary Construction with id " + id + " not found"));
            return
                    constellationArbitraryRepository.saveAndFlush(
                            new coArbitraryConstruction(detailedConstellation)
                    ).getDtoMessage("INSERT", "Arbitrary Construction with id " + id + " updated successfully");

        } catch (EntityNotFoundException ex) {
            return new dtoMessage("UPDATE", "Arbitrary Construction with id " + id + " not found");
        }
    }

    @Transactional
    public dtoMessage updateConstellationArbitraryByList(List<dtoArbitraryConstruction> detailedConstellations) {
        Optional<ConstellationEntity> optionalConstellation = constellationRepository.findById(detailedConstellations.get(0).getTableId());
        if(optionalConstellation.isPresent()){
            for (dtoArbitraryConstruction dtoArbitraryConstruction : detailedConstellations) {
                try {
                    Long id = dtoArbitraryConstruction.getID();
                    if (id == null) {
                        saveNewConstellationArbitrary(dtoArbitraryConstruction, optionalConstellation.get());
                    } else {
                        updateExistingConstellationArbitrary(dtoArbitraryConstruction, id);
                    }
                } catch (EntityNotFoundException exception) {
                    saveNewConstellationArbitrary(dtoArbitraryConstruction, optionalConstellation.get());
                    return new dtoMessage("UPDATE SUCCESS", "Arbitrary Construction update with id " + dtoArbitraryConstruction.getID());
                } catch (Exception exception) {
                    return new dtoMessage("UPDATE ERROR", "An error occurred while updating Arbitrary Construction: " + exception.getMessage());
                }
            }
        }
        recalculationIdNodeConstellation();
        return new dtoMessage("UPDATE SUCCESS", "All Arbitrary Construction updated successfully");
    }

    private void recalculationIdNodeConstellation() {

    }

    private void updateExistingConstellationArbitrary(dtoArbitraryConstruction detailedConstellation, Long id) {
        Optional<coArbitraryConstruction> optionalConstellationDetailed = constellationArbitraryRepository.findById(id);
        if (optionalConstellationDetailed.isPresent()) {
            coArbitraryConstruction existingConstellation = optionalConstellationDetailed.get();

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
            throw new EntityNotFoundException("Arbitrary Construction with id " + id + " not found");
        }
    }



    private void deleteConstellationArbitrary(coArbitraryConstruction coArbitraryConstruction) {
        idNodeRepository.delete(coArbitraryConstruction.getGeneralIdNodeEntity());
        updateIdNodeOfArbitraryConstruction(coArbitraryConstruction.getArbitraryConstructionIdNode());
        constellationArbitraryRepository.delete(coArbitraryConstruction);
    }

    private void updateIdNodeOfArbitraryConstruction(Long idNode) {
        // Получаем список всех записей с idNode >= переданного idNode
        List<generalIdNodeEntity> idNodeEntities = idNodeRepository.findAllByIdNodeGreaterThanEqual(idNode);
        // Обновляем значения idNode
        for (generalIdNodeEntity idNodeEntity : idNodeEntities) {
            idNodeEntity.setIdNode(idNodeEntity.getIdNode() - 1); // Уменьшаем idNode на единицу
        }
        // Сохраняем обновленные записи в базу данных
        idNodeRepository.saveAll(idNodeEntities);
    }

    private void saveNewConstellationArbitrary(dtoArbitraryConstruction detailedConstellation, ConstellationEntity constellation) {
        generalIdNodeEntity generalIdNodeEntity = createNewGeneralIdNodeEntity();
        coArbitraryConstruction coArbitraryConstruction = createNewConstellationArbitrary(
                detailedConstellation, generalIdNodeEntity, constellation
        );
        saveConstellationAndStatus(
                coArbitraryConstruction, generalIdNodeEntity
        );
    }

    private void saveConstellationAndStatus(
            coArbitraryConstruction coArbitraryConstruction,
            generalIdNodeEntity generalIdNodeEntity
    ) {
        idNodeRepository.save(generalIdNodeEntity);
        constellationArbitraryRepository.save(coArbitraryConstruction);
    }

    private coArbitraryConstruction createNewConstellationArbitrary(
            dtoArbitraryConstruction detailedConstellation,
            generalIdNodeEntity generalIdNodeEntity,
            ConstellationEntity constellation) {
        coArbitraryConstruction coArbitraryConstruction = new coArbitraryConstruction(detailedConstellation);
        coArbitraryConstruction.setGeneralIdNodeEntity(generalIdNodeEntity);
        coArbitraryConstruction.setConstellation(constellation);
        return coArbitraryConstruction;
    }

    private generalIdNodeEntity createNewGeneralIdNodeEntity() {
        generalIdNodeEntity generalIdNodeEntity = new generalIdNodeEntity();
        Long maxIdNode = findMaxIdNode();
        generalIdNodeEntity.setIdNode(maxIdNode + 1);
        return generalIdNodeEntity;
    }


    public dtoMessage updateConstellationArbitraryById(Long id, dtoArbitraryConstruction detailedConstellation) {
        try {
            constellationArbitraryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Arbitrary Construction with id " + id + " not found"));
            return
                    constellationArbitraryRepository.saveAndFlush(
                            new coArbitraryConstruction(detailedConstellation)
                    ).getDtoMessage("INSERT", "Arbitrary Construction with id " + id + " updated successfully");

        } catch (EntityNotFoundException ex) {
            return new dtoMessage("UPDATE", "Arbitrary Construction with id " + id + " not found");
        }
    }

    public dtoMessage deleteConstellationArbitraryById(Long id) {
        Optional<coArbitraryConstruction> optionalConstellationArbitrary = constellationArbitraryRepository.findById(id);
        if (optionalConstellationArbitrary.isPresent()) {
            coArbitraryConstruction coArbitraryConstruction = optionalConstellationArbitrary.get();
            constellationArbitraryRepository.delete(coArbitraryConstruction);
            return new dtoMessage("DELETE", "Arbitrary Construction with id " + id + " deleted successfully");
        } else {
            return new dtoMessage("DELETE", "Arbitrary Construction with id " + id + " not found");
        }
    }

    public dtoArbitraryConstruction getConstellationArbitraryById(Long id) {
        return constellationArbitraryRepository.findById(id).orElse(new coArbitraryConstruction()).getDto();
    }

    public Long findMaxIdNode() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(e.idNode) FROM generalIdNodeEntity e", Long.class);
        return query.getSingleResult() != null ? query.getSingleResult() : 0L;
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
}
