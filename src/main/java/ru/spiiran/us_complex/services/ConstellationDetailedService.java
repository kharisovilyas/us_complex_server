package ru.spiiran.us_complex.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoDetailedConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.ConstellationDetailed;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;
import ru.spiiran.us_complex.repositories.ConstellationDetailedRepository;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConstellationDetailedService {
    @Autowired
    private ConstellationDetailedRepository constellationDetailedRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IdNodeRepository idNodeRepository;
    @Autowired
    private StatusGeneralRepository statusGeneralRepository;

    public List<ConstellationDetailed> getAllDetailedConstellations() {
        return constellationDetailedRepository.findAll();
    }

    @Transactional
    public dtoMessage addConstellationDetailed(Long id, dtoDetailedConstellation detailedConstellation) {
        try {
            constellationDetailedRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("EarthPoint with id " + id + " not found"));
            return
                    constellationDetailedRepository.saveAndFlush(
                            new ConstellationDetailed(detailedConstellation)
                    ).getDtoMessage("INSERT", "EarthPoint with id " + id + " updated successfully");

        } catch (EntityNotFoundException ex) {
            return new dtoMessage("UPDATE", "EarthPoint with id " + id + " not found");
        }
    }

    @Transactional
    public dtoMessage updateConstellationDetailedByList(List<dtoDetailedConstellation> detailedConstellations) {
        for (dtoDetailedConstellation dtoDetailedConstellation : detailedConstellations) {
            try {
                Long id = dtoDetailedConstellation.getID();
                if (id == null) {
                    saveNewConstellationDetailed(dtoDetailedConstellation);
                } else {
                    updateExistingConstellation(dtoDetailedConstellation, id);
                }
            } catch (EntityNotFoundException exception) {
                saveNewConstellationDetailed(dtoDetailedConstellation);
                return new dtoMessage("UPDATE SUCCESS", "DetailedConstellation update with id " + dtoDetailedConstellation.getID());
            } catch (Exception exception) {
                return new dtoMessage("UPDATE ERROR", "An error occurred while updating DetailedConstellation: " + exception.getMessage());
            }
        }
        return new dtoMessage("UPDATE SUCCESS", "All EarthPoints updated successfully");
    }

    private void updateExistingConstellation(dtoDetailedConstellation detailedConstellation, Long id) {
        Optional<ConstellationDetailed> optionalConstellationDetailed = constellationDetailedRepository.findById(id);
        if (optionalConstellationDetailed.isPresent()) {
            ConstellationDetailed existingConstellation = optionalConstellationDetailed.get();
            Boolean isDeleted = detailedConstellation.getDeleted();
            if (isDeleted != null && isDeleted) {
                deleteConstellationDetailed(existingConstellation);
            } else {
                ConstellationDetailed updateConstellation = getUpdatedConstellationDetailed(
                        detailedConstellation, existingConstellation
                );
                constellationDetailedRepository.save(updateConstellation);
            }
        } else {
            throw new EntityNotFoundException("ConstellationDetailed with id " + id + " not found");
        }
    }

    private void deleteConstellationDetailed(ConstellationDetailed constellationDetailed) {
        idNodeRepository.delete(constellationDetailed.getGeneralIdNodeEntity());
        constellationDetailedRepository.delete(constellationDetailed);
    }

    private void saveNewConstellationDetailed(dtoDetailedConstellation detailedConstellation) {
        generalIdNodeEntity generalIdNodeEntity = createNewGeneralIdNodeEntity();
        generalStatusEntity generalStatus = createNewGeneralStatus();
        ConstellationDetailed constellationDetailed = createNewConstellationDetailed(
                detailedConstellation, generalIdNodeEntity, generalStatus
        );
        saveConstellationNodeStatus(
                constellationDetailed, generalIdNodeEntity, generalStatus
        );
    }

    private void saveConstellationNodeStatus(
            ConstellationDetailed constellationDetailed,
            generalIdNodeEntity generalIdNodeEntity,
            generalStatusEntity generalStatus
    ) {
        idNodeRepository.save(generalIdNodeEntity);
        statusGeneralRepository.save(generalStatus);
        constellationDetailedRepository.save(constellationDetailed);
    }

    private ConstellationDetailed createNewConstellationDetailed(
            dtoDetailedConstellation detailedConstellation,
            generalIdNodeEntity generalIdNodeEntity,
            generalStatusEntity generalStatus
    ) {
        ConstellationDetailed constellationDetailed = new ConstellationDetailed(detailedConstellation);
        constellationDetailed.setGeneralIdNodeEntity(generalIdNodeEntity);
        constellationDetailed.setGeneralStatus(generalStatus);
        return constellationDetailed;
    }

    private generalStatusEntity createNewGeneralStatus() {
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


    public dtoMessage updateConstellationDetailedById(Long id, dtoDetailedConstellation detailedConstellation) {
        try {
            constellationDetailedRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("EarthPoint with id " + id + " not found"));
            return
                    constellationDetailedRepository.saveAndFlush(
                            new ConstellationDetailed(detailedConstellation)
                    ).getDtoMessage("INSERT", "EarthPoint with id " + id + " updated successfully");

        } catch (EntityNotFoundException ex) {
            return new dtoMessage("UPDATE", "EarthPoint with id " + id + " not found");
        }
    }

    public dtoMessage deleteConstellationDetailedById(Long id) {
        Optional<ConstellationDetailed> optionalConstellationDetailed = constellationDetailedRepository.findById(id);
        if (optionalConstellationDetailed.isPresent()) {
            ConstellationDetailed constellationDetailed = optionalConstellationDetailed.get();
            constellationDetailedRepository.delete(constellationDetailed);
            return new dtoMessage("DELETE", "EarthPoint with id " + id + " deleted successfully");
        } else {
            return new dtoMessage("DELETE", "EarthPoint with id " + id + " not found");
        }
    }

    public dtoDetailedConstellation getConstellationDetailedById(Long id) {
        return constellationDetailedRepository.findById(id).orElse(new ConstellationDetailed()).getDto();
    }

    public Long findMaxIdNode() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(e.idNode) FROM generalIdNodeEntity e", Long.class);
        return query.getSingleResult() != null ? query.getSingleResult() : 0L;
    }

    private ConstellationDetailed getUpdatedConstellationDetailed(dtoDetailedConstellation dtoDetailedConstellation, ConstellationDetailed existingConstellation) {
        existingConstellation.setAltitude(dtoDetailedConstellation.getAltitude());
        existingConstellation.setEccentricity(dtoDetailedConstellation.getEccentricity());
        existingConstellation.setIncline(dtoDetailedConstellation.getIncline());
        existingConstellation.setModelSat(dtoDetailedConstellation.getModelSat());
        existingConstellation.setTrueAnomaly(dtoDetailedConstellation.getTrueAnomaly());
        existingConstellation.setLongitudeAscendingNode(dtoDetailedConstellation.getLongitudeAscendingNode());
        existingConstellation.setPerigeeWidthArgument(dtoDetailedConstellation.getPerigeeWidthArgument());
        return existingConstellation;
    }

    @Transactional
    public dtoMessage setTableStatusOfEdit(Boolean status) {
        generalStatusEntity statusEntity = statusGeneralRepository.findSingleStatus();
        if (statusEntity != null) {
            statusEntity.setStatusOfEditEarth(status);
            statusGeneralRepository.save(statusEntity);
            return new dtoMessage("SUCCESS TO ADD STATUS", "Table status updated successfully");
        } else {
            // Обработка случая, если запись статуса не найдена
            return new dtoMessage("ERROR TO ADD STATUS", "Status entity not found");
        }
    }
}
