package ru.spiiran.us_complex.services.earth;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.SystemEntity;
import ru.spiiran.us_complex.repositories.NodeIdRepository;
import ru.spiiran.us_complex.repositories.constellation.ConstellationArbitraryRepository;
import ru.spiiran.us_complex.repositories.earth.EarthPointRepository;
import ru.spiiran.us_complex.repositories.satrequest.SystemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EarthPointService {
    @Autowired
    private EarthPointRepository earthPointRepository;
    @Autowired
    private ConstellationArbitraryRepository arbitraryRepository;
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private NodeIdRepository nodeRepository;

    public List<dtoEarthPoint> getAllEarthPoints() {
        return earthPointRepository
                .findAll()
                .stream()
                .map(dtoEarthPoint::new)
                .collect(Collectors.toList());
    }


    @Transactional
    public dtoMessage updatePointByList(List<dtoEarthPoint> dtoListOfEarthPoint) {
        for (dtoEarthPoint earthPoint : dtoListOfEarthPoint) {
            try {
                Long id = earthPoint.getID();
                if (id == null) {
                    saveNewEarthPoint(earthPoint);
                } else {
                    updateExistingEarthPoint(earthPoint);
                }

            } catch (EntityNotFoundException exception) {
                saveNewEarthPoint(earthPoint);
                return new dtoMessage("UPDATE SUCCESS", "EarthPoint updated with id " + earthPoint.getID());
            } catch (Exception exception) {
                exception.printStackTrace();
                return new dtoMessage("UPDATE ERROR", "An error occurred while updating EarthPoint: " + exception.getMessage());
            }
        }

        return new dtoMessage("UPDATE SUCCESS", "All EarthPoints updated successfully");
    }

    private void saveNewEarthPoint(dtoEarthPoint earthPoint) {
        EarthPointEntity earthPointEntity = new EarthPointEntity(earthPoint);
        IdNodeEntity idNodeEntity = new IdNodeEntity(
                findMaxEarthNode(),
                "GS"
        );
        earthPointEntity.setIdNodeEntity(
                idNodeEntity
        );
        nodeRepository.save(
                idNodeEntity
        );
        earthPointRepository.save(
                earthPointEntity
        );
        arbitraryRepository.findAllByOrderByNodeIdAsc()
                .forEach(this::incrementNodeId);
    }

    private void incrementNodeId(coArbitraryConstruction coArbitraryConstruction) {
        IdNodeEntity idNodeEntity = coArbitraryConstruction.getIdNodeEntity();
        idNodeEntity.setNodeId(idNodeEntity.getNodeId() + 1);
        nodeRepository.save(idNodeEntity);
    }

    private Long findMaxEarthNode() {
        Long maxEarthPointNode = nodeRepository.findMaxNodeIdWithEarthPoint();
        return maxEarthPointNode == null? 0L : maxEarthPointNode;
    }

    private void updateExistingEarthPoint(dtoEarthPoint earthPoint) {
        Optional<EarthPointEntity> optionalEarthPointEntity = earthPointRepository.findById(earthPoint.getID());
        if (optionalEarthPointEntity.isPresent()) {
            EarthPointEntity existingPoint = optionalEarthPointEntity.get();
            Boolean isDeleted = earthPoint.getDeleted();
            if (isDeleted != null && isDeleted) {
                deletePoint(existingPoint);
            } else {
                earthPointRepository.saveAndFlush(
                        new EarthPointEntity(existingPoint, earthPoint)
                );
            }
        } else {
            throw new EntityNotFoundException("EarthPoint with id " + earthPoint.getID() + " not found");
        }
    }

    private void deletePoint(EarthPointEntity earthPointEntity) {
        nodeRepository.delete(earthPointEntity.getIdNodeEntity());
        earthPointRepository.delete(earthPointEntity);

        Long deletedNodeId = earthPointEntity.getIdNodeEntity().getNodeId();

        // Обновляем nodeId у EarthPointEntity:
        earthPointRepository.findAllByNodeIdGreaterThan(deletedNodeId)
                .forEach(this::decrementEarthPointNodeId);

        // Обновляем nodeId у coArbitraryConstruction:
        arbitraryRepository.findAllByNodeIdGreaterThan(deletedNodeId)
                .forEach(this::decrementArbitraryConstructionNodeId);
    }

    private void decrementEarthPointNodeId(EarthPointEntity earthPointEntity) {
        IdNodeEntity idNodeEntity = earthPointEntity.getIdNodeEntity();
        idNodeEntity.setNodeId(idNodeEntity.getNodeId() - 1);
        nodeRepository.save(idNodeEntity);
        earthPointRepository.save(earthPointEntity); // Сохраняем EarthPointEntity
    }

    private void decrementArbitraryConstructionNodeId(coArbitraryConstruction coArbitraryConstruction) {
        IdNodeEntity idNodeEntity = coArbitraryConstruction.getIdNodeEntity();
        idNodeEntity.setNodeId(idNodeEntity.getNodeId() - 1);
        nodeRepository.save(idNodeEntity);
        arbitraryRepository.save(coArbitraryConstruction); // Сохраняем coArbitraryConstruction
    }

    public dtoMessage setSystemParams(Boolean status) {
        Optional<SystemEntity> optionalSystemEntity = systemRepository.findById(1L);
        if (optionalSystemEntity.isPresent()) {
            SystemEntity systemEntity = optionalSystemEntity.get();
            systemEntity.setEarthStatus(status);
            systemRepository.save(systemEntity);
            return new dtoMessage("APPROVE SUCCESS", "System update");
        } else {
            SystemEntity systemEntity = new SystemEntity();
            systemEntity.setEarthStatus(status);
            systemRepository.save(systemEntity);
            return new dtoMessage("APPROVE SUCCESS", "System added");
        }
    }
}