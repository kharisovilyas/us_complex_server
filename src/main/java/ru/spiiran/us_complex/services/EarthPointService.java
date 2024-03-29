package ru.spiiran.us_complex.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;
import ru.spiiran.us_complex.repositories.earth.EarthPointRepository;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EarthPointService {
    @Autowired
    private EarthPointRepository earthPointRepository;
    @Autowired
    private StatusGeneralRepository statusRepository;
    @Autowired
    private IdNodeRepository idNodeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public dtoMessage addEarthPoint(dtoEarthPoint earthPoint) {
        try {
            EarthPointEntity earthPointEntity = new EarthPointEntity(earthPoint);
            generalIdNodeEntity generalIdNodeEntity = new generalIdNodeEntity();
            earthPointEntity.setGeneralIdNodeEntity(generalIdNodeEntity);
            idNodeRepository.save(generalIdNodeEntity);
            return earthPointRepository.saveAndFlush(earthPointEntity)
                    .getDtoMessage("INSERT SUCCESS", "EarthPoint added successfully");
        } catch (Exception exception) {
            return new dtoMessage("INSERT ERROR", "Failed to add EarthPoint");
        }
    }

    public dtoEarthPoint getEarthPointById(Long id) {
        return earthPointRepository.findById(id).orElse(new EarthPointEntity()).getDto();
    }

    public List<EarthPointEntity> getAllEarthPoints() {
        return earthPointRepository.findAll();
    }


    public dtoMessage updatePointById(Long id, dtoEarthPoint updateEarthPoint) {
        Optional<EarthPointEntity> optionalEarthPointEntity = earthPointRepository.findById(id);
        if (optionalEarthPointEntity.isPresent()) {
            EarthPointEntity existingPoint = optionalEarthPointEntity.get();
            existingPoint.setNameEarthPoint(updateEarthPoint.getNameEarthPoint());
            existingPoint.setLongitude(updateEarthPoint.getLongitude());
            existingPoint.setLatitude(updateEarthPoint.getLatitude());
            return
                    earthPointRepository.saveAndFlush(
                            existingPoint
                    ).getDtoMessage("UPDATE SUCCESS", "EarthPoint with id " + id + " updated successfully");

        } else {
            return new dtoMessage("UPDATE ERROR", "EarthPoint with id " + id + " not found");
        }
    }

    @Transactional
    public dtoMessage deletePointById(Long id) {
        Optional<EarthPointEntity> optionalEarthPointEntity = earthPointRepository.findById(id);
        if (optionalEarthPointEntity.isPresent()) {
            EarthPointEntity earthPointEntity = optionalEarthPointEntity.get();
            idNodeRepository.delete(earthPointEntity.getGeneralIdNodeEntity());
            earthPointRepository.delete(earthPointEntity);
            return new dtoMessage("DELETE SUCCESS", "EarthPoint with id " + id + " deleted successfully");
        } else {
            return new dtoMessage("DELETE ERROR", "EarthPoint with id " + id + " not found");
        }
    }


    @Transactional
    public dtoMessage updatePointByList(List<dtoEarthPoint> dtoListOfEarthPoint) {
        for (dtoEarthPoint earthPoint : dtoListOfEarthPoint) {
            try {
                Long id = earthPoint.getID();
                if (id == null) {
                    saveNewEarthPoint(earthPoint);
                } else {
                    updateExistingEarthPoint(earthPoint, id);
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
        generalIdNodeEntity generalIdNodeEntity = createNewGeneralIdNodeEntity();
        generalStatusEntity generalStatus = createNewGeneralStatusEntity();
        EarthPointEntity earthPointEntity = createNewEarthPointEntity(earthPoint, generalIdNodeEntity, generalStatus);
        saveEarthPointNodeStatus(earthPointEntity, generalIdNodeEntity, generalStatus);
    }

    private generalStatusEntity createNewGeneralStatusEntity() {
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

    private EarthPointEntity createNewEarthPointEntity(dtoEarthPoint earthPoint, generalIdNodeEntity generalIdNodeEntity, generalStatusEntity generalStatus) {
        EarthPointEntity earthPointEntity = new EarthPointEntity(earthPoint);
        earthPointEntity.setGeneralIdNodeEntity(generalIdNodeEntity);
        earthPointEntity.setGeneralStatus(generalStatus);
        return earthPointEntity;
    }

    private void saveEarthPointNodeStatus(
            EarthPointEntity earthPointEntity,
            generalIdNodeEntity generalIdNodeEntity,
            generalStatusEntity generalStatusEntity
    ) {
        idNodeRepository.save(generalIdNodeEntity);
        statusRepository.save(generalStatusEntity);
        earthPointRepository.save(earthPointEntity);
    }

    private void updateExistingEarthPoint(dtoEarthPoint earthPoint, Long id) {
        Optional<EarthPointEntity> optionalEarthPointEntity = earthPointRepository.findById(id);
        if (optionalEarthPointEntity.isPresent()) {
            EarthPointEntity existingPoint = optionalEarthPointEntity.get();
            Boolean isDeleted = earthPoint.getDeleted();
            if (isDeleted != null && isDeleted) {
                deletePoint(existingPoint);
            } else {
                EarthPointEntity updatedPoint = getUpdatedEarthPoint(earthPoint, existingPoint);
                earthPointRepository.saveAndFlush(updatedPoint);
            }
        } else {
            throw new EntityNotFoundException("EarthPoint with id " + id + " not found");
        }
    }

    private void deletePoint(EarthPointEntity earthPointEntity) {
        idNodeRepository.delete(earthPointEntity.getGeneralIdNodeEntity());
        updateIdNodeOfEarthPoints(earthPointEntity.getGeneralIdNodeEntity().getIdNode());
        earthPointRepository.delete(earthPointEntity);
    }

    private void updateIdNodeOfEarthPoints(Long idNode) {
        // Получаем список всех записей с idNode >= переданного idNode
        List<generalIdNodeEntity> idNodeEntities = idNodeRepository.findAllByIdNodeGreaterThanEqual(idNode);
        // Обновляем значения idNode
        for (generalIdNodeEntity idNodeEntity : idNodeEntities) {
            idNodeEntity.setIdNode(idNodeEntity.getIdNode() - 1); // Уменьшаем idNode на единицу
        }
        // Сохраняем обновленные записи в базу данных
        idNodeRepository.saveAll(idNodeEntities);
    }


    public Long findMaxIdNode() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(e.idNode) FROM generalIdNodeEntity e", Long.class);
        return query.getSingleResult() != null ? query.getSingleResult() : 0L;
    }

    private EarthPointEntity getUpdatedEarthPoint(dtoEarthPoint earthPoint, EarthPointEntity existingPoint) {
        existingPoint.setNameEarthPoint(earthPoint.getNameEarthPoint());
        existingPoint.setLongitude(earthPoint.getLongitude());
        existingPoint.setLatitude(earthPoint.getLatitude());
        return existingPoint;
    }


    @Transactional
    public dtoMessage setTableStatusOfEdit(Boolean status) {
        generalStatusEntity statusEntity = statusRepository.findSingleStatus();
        if (statusEntity != null) {
            statusEntity.setStatusOfEditEarth(status);
            statusRepository.save(statusEntity);
            return new dtoMessage("SUCCESS TO ADD STATUS", "Table status updated successfully");
        } else {
            // Обработка случая, если запись статуса не найдена
            return new dtoMessage("ERROR TO ADD STATUS", "Status entity not found");
        }
    }

}
