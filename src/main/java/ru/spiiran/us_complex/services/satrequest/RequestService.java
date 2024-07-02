package ru.spiiran.us_complex.services.satrequest;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.general.dtoIdNode;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.satrequest.dtoCatalog;
import ru.spiiran.us_complex.model.dto.satrequest.dtoRequest;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.CatalogEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.RequestEntity;
import ru.spiiran.us_complex.repositories.NodeIdRepository;
import ru.spiiran.us_complex.repositories.earth.EarthPointRepository;
import ru.spiiran.us_complex.repositories.satrequest.CatalogRepository;
import ru.spiiran.us_complex.repositories.satrequest.RequestRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private NodeIdRepository nodeRepository;
    @Autowired
    private EarthPointRepository earthPointRepository;

    public List<dtoRequest> getAllRequests() {
        return requestRepository
                .findAll()
                .stream()
                .map(dtoRequest::new)
                .collect(Collectors.toList());
    }

    public dtoMessage updateCatalogByList(List<dtoCatalog> catalogList) {
        for (dtoCatalog catalog : catalogList) {
            if (catalog.getDeleted() != null && catalog.getDeleted()) {
                catalogRepository.delete(new CatalogEntity(catalog));
            } else {
                catalogRepository.saveAndFlush(new CatalogEntity(catalog));
            }
        }
        return new dtoMessage("SUCCESS", "All catalog update");
    }

    public List<dtoCatalog> getAllCatalogs() {
        return catalogRepository.findAll()
                .stream()
                .map(dtoCatalog::new)
                .collect(Collectors.toList());
    }

    public dtoMessage updateRequestByList(List<dtoRequest> requests) {
        try {
            for (dtoRequest request : requests) {
                if (request.getDeleted() != null && request.getDeleted()) {
                    requestRepository.delete(new RequestEntity(request));
                } else {
                    RequestEntity requestEntity = new RequestEntity(request);
                    EarthPointEntity earthPoint = findExistingEarthPoint(request.getEarthPoint());
                    CatalogEntity catalogEntity = findCatalogEntity(request.getCatalog());
                    requestEntity.setEarthPoint(earthPoint);
                    requestEntity.setCatalogEntity(catalogEntity);
                    requestRepository.saveAndFlush(requestEntity);
                }

            }
            return new dtoMessage("SUCCESS", "All requests update");
        } catch (EntityNotFoundException exception) {
            return new dtoMessage("ERROR", exception.getMessage());
        }
    }

    private EarthPointEntity findExistingEarthPoint(dtoEarthPoint earthPoint) throws EntityNotFoundException {
        Optional<EarthPointEntity> optionalEarthPointEntity = earthPointRepository.findById(earthPoint.getID());
        if (optionalEarthPointEntity.isPresent()) {
            return optionalEarthPointEntity.get();
        } else {
            throw new EntityNotFoundException("Earth Point with " + earthPoint.getID() + " not exist");
        }
    }

    private CatalogEntity findCatalogEntity(dtoCatalog catalog) throws EntityNotFoundException {
        Optional<CatalogEntity> optionalCatalogEntity = catalogRepository.findById(catalog.getGoalId());
        if (optionalCatalogEntity.isPresent()) {
            return optionalCatalogEntity.get();
        } else {
            throw new EntityNotFoundException("Catalog with " + catalog.getGoalId() + " not exist");
        }
    }

    private IdNodeEntity findGeneralIdNode(dtoIdNode dtoIdNode) throws EntityNotFoundException {
        Optional<IdNodeEntity> optionalGeneralIdNode = nodeRepository.findById(dtoIdNode.getEntryId());
        if (optionalGeneralIdNode.isPresent()) {
            return optionalGeneralIdNode.get();
        } else {
            throw new EntityNotFoundException("Node with " + dtoIdNode.getIdNode() + " not exist");
        }
    }

    public List<dtoIdNode> getAllIdNode() {
        return nodeRepository
                .findAll()
                .stream()
                .map(dtoIdNode::new)
                .collect(Collectors.toList());
    }
}
