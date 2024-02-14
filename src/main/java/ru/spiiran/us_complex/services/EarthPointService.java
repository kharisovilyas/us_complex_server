package ru.spiiran.us_complex.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.dtoListOfEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;
import ru.spiiran.us_complex.repositories.EarthPointRepository;
import ru.spiiran.us_complex.repositories.IdNodeRepository;
import ru.spiiran.us_complex.repositories.StatusGeneralRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EarthPointService {
    @Autowired
    private EarthPointRepository earthPointRepository;
    @Autowired
    private StatusGeneralRepository statusGeneralRepository;
    @Autowired
    private IdNodeRepository idNodeRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public dtoMessage addEarthPoint(dtoEarthPoint earthPoint) {
        try {
            EarthPointEntity earthPointEntity = new EarthPointEntity(earthPoint);

            generalStatusEntity generalStatusEntity = new generalStatusEntity();
            statusGeneralRepository.save(generalStatusEntity);
            earthPointEntity.setStatusGeneral(generalStatusEntity);
            generalIdNodeEntity generalIdNodeEntity = new generalIdNodeEntity();
            idNodeRepository.save(generalIdNodeEntity);
            earthPointEntity.setGeneralIdNodeEntity(generalIdNodeEntity);

            statusGeneralRepository.saveAndFlush(generalStatusEntity);
            return earthPointRepository.saveAndFlush(earthPointEntity).getDtoMessage("INSERT", "EarthPoint added successfully");
        } catch (Exception e) {
            return new dtoMessage("ERROR", "Failed to add EarthPoint");
        }
    }

    public dtoEarthPoint getEarthPointById(Long id) {
        return earthPointRepository.findById(id).orElse(new EarthPointEntity()).getDto();
    }

    public List<EarthPointEntity> getAllEarthPoints() {
        return earthPointRepository.findAll();
    }

    @Transactional
    public dtoMessage setStatusOfEdit(Boolean newStatus) {
        try {
            List<generalStatusEntity> statusGeneralEntities = statusGeneralRepository.findAll();
            statusGeneralEntities.forEach(generalStatusEntity -> generalStatusEntity.setStatusOfEdit(newStatus));
            statusGeneralRepository.saveAll(statusGeneralEntities);
            List<EarthPointEntity> earthPoints = earthPointRepository.findAll();
            earthPoints.forEach(earthPoint -> {
                if (earthPoint.getStatusGeneral() == null) {
                    generalStatusEntity generalStatusEntity = new generalStatusEntity();
                    generalStatusEntity.setStatusOfEdit(newStatus);

                    earthPoint.setStatusGeneral(generalStatusEntity);
                } else {
                    earthPoint.getStatusGeneral().setStatusOfEdit(newStatus);
                }
            });
            earthPointRepository.saveAll(earthPoints);

            return new dtoMessage("UPDATE", "Status Of Edit of all Points updated successfully");
        } catch (Exception e) {
            // Обработка ошибок, если не удалось обновить статусы
            return new dtoMessage("ERROR", "Failed to update Status Of Edit");
        }
    }


    public dtoMessage updatePointById(Long id, dtoEarthPoint updateEarthPoint) {
        try {

            earthPointRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("EarthPoint with id " + id + " not found"));
            return
                    earthPointRepository.saveAndFlush(
                            new EarthPointEntity(updateEarthPoint)
                    ).getDtoMessage("INSERT", "EarthPoint with id " + id + " updated successfully");

        } catch (EntityNotFoundException ex) {
            return new dtoMessage("UPDATE", "EarthPoint with id " + id + " not found");
        }
    }

    @Transactional
    public dtoMessage deletePointById(Long id) {
        Optional<EarthPointEntity> optionalEarthPointEntity = earthPointRepository.findById(id);
        if (optionalEarthPointEntity.isPresent()) {
            EarthPointEntity earthPointEntity = optionalEarthPointEntity.get();
            idNodeRepository.delete(earthPointEntity.getGeneralIdNodeEntity());
            statusGeneralRepository.delete(earthPointEntity.getStatusGeneral());
            earthPointRepository.delete(earthPointEntity);
            return new dtoMessage("DELETE", "EarthPoint with id " + id + " deleted successfully");
        } else {
            return new dtoMessage("DELETE", "EarthPoint with id " + id + " not found");
        }
    }
    @Transactional
    public dtoMessage updatePointByList(dtoListOfEarthPoint dtoListOfEarthPoint) {
        try {
            // Перебор всех элементов списка и сохранение или обновление их в базе данных
            for (dtoEarthPoint earthPoint : dtoListOfEarthPoint.getDtoEarthPointList()) {
                // Проверяем, существует ли объект с данным ID
                Long ID = earthPoint.getID();
                if (ID == null) {
                    EarthPointEntity earthPointEntity = new EarthPointEntity();
                    earthPointEntity.setNameEarthPoint(earthPoint.getNameEarthPoint());
                    earthPointEntity.setLongitude(earthPoint.getLongitude());
                    earthPointEntity.setLatitude(earthPoint.getLatitude());

                    generalStatusEntity generalStatusEmpty = new generalStatusEntity();
                    statusGeneralRepository.save(generalStatusEmpty);
                    earthPointEntity.setStatusGeneral(generalStatusEmpty);

                    generalIdNodeEntity generalIdNodeEntity = new generalIdNodeEntity();
                    Long maxIdNode = findMaxIdNode();
                    generalIdNodeEntity.setIdNode(maxIdNode + 1);
                    idNodeRepository.save(generalIdNodeEntity);
                    earthPointEntity.setGeneralIdNodeEntity(generalIdNodeEntity);

                    earthPointRepository.save(earthPointEntity);
                } else {
                    Optional<EarthPointEntity> optionalEarthPointEntity = earthPointRepository.findById(earthPoint.getID());
                    if (optionalEarthPointEntity.isPresent()) {
                        EarthPointEntity existingPoint = optionalEarthPointEntity.get();

                        existingPoint.setNameEarthPoint(earthPoint.getNameEarthPoint());
                        existingPoint.setLongitude(earthPoint.getLongitude());
                        existingPoint.setLatitude(earthPoint.getLatitude());

                        generalStatusEntity generalStatusEmpty = new generalStatusEntity();
                        statusGeneralRepository.save(generalStatusEmpty);
                        existingPoint.setStatusGeneral(generalStatusEmpty);

                        //setGeneralIdNodeEntity(generalIdNodeEntity) - not needed

                        earthPointRepository.save(existingPoint);
                    }
                }

            }

            return new dtoMessage("SUCCESS", "All EarthPoints updated successfully");
        } catch (Exception e) {
            return new dtoMessage("ERROR", "Failed to update EarthPoints: " + e.getMessage());
        }
    }

    private Long findMaxIdNode() {
        TypedQuery<Long> query = entityManager.createQuery("SELECT MAX(e.idNode) FROM generalIdNodeEntity e", Long.class);
        return query.getSingleResult() != null ? query.getSingleResult() : 0L;
    }

    @Transactional
    public dtoMessage addEarthPointByList(dtoListOfEarthPoint dtoListOfEarthPoint) {
        try {
            // Перебор всех элементов списка и сохранение их в базу данных
            for (dtoEarthPoint earthPoint : dtoListOfEarthPoint.getDtoEarthPointList()) {
                EarthPointEntity earthPointEntity = new EarthPointEntity();
                earthPointEntity.setNameEarthPoint(earthPoint.getNameEarthPoint());
                earthPointEntity.setLongitude(earthPoint.getLongitude());
                earthPointEntity.setLatitude(earthPoint.getLatitude());
                earthPointRepository.save(earthPointEntity);
            }

            return new dtoMessage("SUCCESS", "All EarthPoints added successfully");
        } catch (Exception e) {
            return new dtoMessage("ERROR", "Failed to add EarthPoints: " + e.getMessage());
        }
    }

}
