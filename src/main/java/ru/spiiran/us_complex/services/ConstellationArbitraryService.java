package ru.spiiran.us_complex.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellationArbitrary;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.ConstellationArbitrary;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.repositories.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ConstellationArbitraryService {
    @Autowired
    private ConstellationArbitraryRepository constellationArbitraryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IdNodeRepository idNodeRepository;
    @Autowired
    private StatusGeneralRepository statusGeneralRepository;

    public List<ConstellationArbitrary> getAllDetailedConstellations() {
        return constellationArbitraryRepository.findAll();
    }

    @Transactional
    public dtoMessage addConstellationArbitrary(Long id, dtoConstellationArbitrary detailedConstellation) {
        try {
            constellationArbitraryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("EarthPoint with id " + id + " not found"));
            return
                    constellationArbitraryRepository.saveAndFlush(
                            new ConstellationArbitrary(detailedConstellation)
                    ).getDtoMessage("INSERT", "EarthPoint with id " + id + " updated successfully");

        } catch (EntityNotFoundException ex) {
            return new dtoMessage("UPDATE", "EarthPoint with id " + id + " not found");
        }
    }

    @Transactional
    public dtoMessage updateConstellationArbitraryByList(List<dtoConstellationArbitrary> detailedConstellations) {
        for (dtoConstellationArbitrary dtoConstellationArbitrary : detailedConstellations) {
            try {
                Long id = dtoConstellationArbitrary.getID();
                if (id == null) {
                    saveNewConstellationArbitrary(dtoConstellationArbitrary);
                } else {
                    updateExistingConstellationArbitrary(dtoConstellationArbitrary, id);
                }
            } catch (EntityNotFoundException exception) {
                saveNewConstellationArbitrary(dtoConstellationArbitrary);
                return new dtoMessage("UPDATE SUCCESS", "DetailedConstellation update with id " + dtoConstellationArbitrary.getID());
            } catch (Exception exception) {
                return new dtoMessage("UPDATE ERROR", "An error occurred while updating DetailedConstellation: " + exception.getMessage());
            }
        }
        return new dtoMessage("UPDATE SUCCESS", "All EarthPoints updated successfully");
    }

    private void updateExistingConstellationArbitrary(dtoConstellationArbitrary detailedConstellation, Long id) {
        Optional<ConstellationArbitrary> optionalConstellationDetailed = constellationArbitraryRepository.findById(id);
        if (optionalConstellationDetailed.isPresent()) {
            ConstellationArbitrary existingConstellation = optionalConstellationDetailed.get();
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
            throw new EntityNotFoundException("ConstellationDetailed with id " + id + " not found");
        }
    }

    private void deleteConstellationArbitrary(ConstellationArbitrary constellationArbitrary) {
        idNodeRepository.delete(constellationArbitrary.getGeneralIdNodeEntity());
        constellationArbitraryRepository.delete(constellationArbitrary);
    }

    private void saveNewConstellationArbitrary(dtoConstellationArbitrary detailedConstellation) {
        generalIdNodeEntity generalIdNodeEntity = createNewGeneralIdNodeEntity();
        ConstellationArbitrary constellationArbitrary = createNewConstellationArbitrary(
                detailedConstellation, generalIdNodeEntity
        );
        saveConstellationAndStatus(
                constellationArbitrary, generalIdNodeEntity
        );
    }

    private void saveConstellationAndStatus(
            ConstellationArbitrary constellationArbitrary,
            generalIdNodeEntity generalIdNodeEntity
    ) {
        idNodeRepository.save(generalIdNodeEntity);
        constellationArbitraryRepository.save(constellationArbitrary);
    }

    private ConstellationArbitrary createNewConstellationArbitrary(
            dtoConstellationArbitrary detailedConstellation,
            generalIdNodeEntity generalIdNodeEntity
    ) {
        ConstellationArbitrary constellationArbitrary = new ConstellationArbitrary(detailedConstellation);
        constellationArbitrary.setGeneralIdNodeEntity(generalIdNodeEntity);
        return constellationArbitrary;
    }

    private generalIdNodeEntity createNewGeneralIdNodeEntity() {
        generalIdNodeEntity generalIdNodeEntity = new generalIdNodeEntity();
        Long maxIdNode = findMaxIdNode();
        generalIdNodeEntity.setIdNode(maxIdNode + 1);
        return generalIdNodeEntity;
    }


    public dtoMessage updateConstellationArbitraryById(Long id, dtoConstellationArbitrary detailedConstellation) {
        try {
            constellationArbitraryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("EarthPoint with id " + id + " not found"));
            return
                    constellationArbitraryRepository.saveAndFlush(
                            new ConstellationArbitrary(detailedConstellation)
                    ).getDtoMessage("INSERT", "EarthPoint with id " + id + " updated successfully");

        } catch (EntityNotFoundException ex) {
            return new dtoMessage("UPDATE", "EarthPoint with id " + id + " not found");
        }
    }

    public dtoMessage deleteConstellationArbitraryById(Long id) {
        Optional<ConstellationArbitrary> optionalConstellationArbitrary = constellationArbitraryRepository.findById(id);
        if (optionalConstellationArbitrary.isPresent()) {
            ConstellationArbitrary constellationArbitrary = optionalConstellationArbitrary.get();
            constellationArbitraryRepository.delete(constellationArbitrary);
            return new dtoMessage("DELETE", "EarthPoint with id " + id + " deleted successfully");
        } else {
            return new dtoMessage("DELETE", "EarthPoint with id " + id + " not found");
        }
    }

    public dtoConstellationArbitrary getConstellationArbitraryById(Long id) {
        return constellationArbitraryRepository.findById(id).orElse(new ConstellationArbitrary()).getDto();
    }

    public Long findMaxIdNode() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(e.idNode) FROM generalIdNodeEntity e", Long.class);
        return query.getSingleResult() != null ? query.getSingleResult() : 0L;
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
}
