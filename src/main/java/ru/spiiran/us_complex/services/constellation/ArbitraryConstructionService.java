package ru.spiiran.us_complex.services.constellation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.constellation.dtoArbitraryConstruction;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.repositories.NodeIdRepository;
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
    private NodeIdRepository nodeRepository;

    @Transactional
    public dtoMessage updateConstellationArbitraryByList(List<dtoArbitraryConstruction> detailedConstellations) {

        Optional<ConstellationEntity> optionalConstellation = Optional.empty(); // По умолчанию - пустой Optional

        // Проверяем размер списка и наличие tableId *перед* вызовом findById:
        if (!detailedConstellations.isEmpty() && detailedConstellations.get(0).getTableId() != null) {
            Long tableId = detailedConstellations.get(0).getTableId();
            optionalConstellation = constellationRepository.findById(tableId);
        }

        // Дальнейшая обработка optionalConstellation:
        if (optionalConstellation.isPresent()) {
            for (dtoArbitraryConstruction dtoArbitraryConstruction : detailedConstellations) {
                try {
                    Long id = dtoArbitraryConstruction.getID();
                    if (id == null) {
                        //TODO: существует одна проблема с optionalConstellation.get() - когда приходит список,
                        // некоторые строчки можно добавить с той же constellation id но в таблице нету записей о ней
                        // - из-за этого не настраивается связь с таблицей constellation И construction
                        saveNewConstellationArbitrary(
                                dtoArbitraryConstruction,
                                optionalConstellation.get()
                        );
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
        } else {
            return new dtoMessage("UPDATE ERROR", "Constellation not added");
        }


        recalculationIdNodeConstellation();
        return new dtoMessage("UPDATE SUCCESS", "All Arbitrary Construction updated successfully");
    }

    private void recalculationIdNodeConstellation() {

    }

    private void updateExistingConstellationArbitrary(dtoArbitraryConstruction dtoArbitraryConstruction, Long id) {
        Optional<coArbitraryConstruction> optionalConstellationDetailed = constellationArbitraryRepository.findById(id);
        if (optionalConstellationDetailed.isPresent()) {
            coArbitraryConstruction existingConstellation = optionalConstellationDetailed.get();
            Boolean isDeleted = dtoArbitraryConstruction.getDeleted();
            if (isDeleted != null && isDeleted) {
                deleteConstellationArbitrary(existingConstellation);
            } else {
                coArbitraryConstruction updateConstellation =
                        new coArbitraryConstruction(existingConstellation, dtoArbitraryConstruction);
                constellationArbitraryRepository.save(updateConstellation);
            }
        } else {
            throw new EntityNotFoundException("Arbitrary Construction with id " + id + " not found");
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

    private void saveNewConstellationArbitrary(dtoArbitraryConstruction detailedConstellation, ConstellationEntity constellation) {
        IdNodeEntity nodeEntity = new IdNodeEntity(
                findMaxIdNode(),
                "GS"
        );
        coArbitraryConstruction coArbitraryConstruction = createNewConstellationArbitrary(
                detailedConstellation, nodeEntity, constellation
        );
        saveConstellationAndStatus(
                coArbitraryConstruction, nodeEntity
        );
    }

    private Long findMaxIdNode() {
        Long maxIdNode = nodeRepository.findMaxIdNode();
        return maxIdNode == null? 0L : maxIdNode;
    }

    private void saveConstellationAndStatus(
            coArbitraryConstruction coArbitraryConstruction,
            IdNodeEntity nodeEntity
    ) {
        nodeRepository.save(nodeEntity);
        constellationArbitraryRepository.save(coArbitraryConstruction);
    }

    private coArbitraryConstruction createNewConstellationArbitrary(
            dtoArbitraryConstruction detailedConstellation,
            IdNodeEntity nodeEntity,
            ConstellationEntity constellation
    ) {
        coArbitraryConstruction coArbitraryConstruction = new coArbitraryConstruction(detailedConstellation);
        coArbitraryConstruction.setIdNodeEntity(nodeEntity);
        coArbitraryConstruction.setConstellation(constellation);
        return coArbitraryConstruction;
    }

}
