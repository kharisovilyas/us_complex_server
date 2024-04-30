package ru.spiiran.us_complex.model.dto.satrequest;

import ru.spiiran.us_complex.model.dto.general.dtoIdNode;
import ru.spiiran.us_complex.model.entitys.satrequest.RequestEntity;

import java.time.LocalDateTime;

public class dtoRequest {
    public dtoRequest() {
    }
    public dtoRequest(RequestEntity requestEntity) {
        this.catalog = new dtoCatalog(requestEntity.getCatalogEntity());
        this.filter = requestEntity.getFilter();
        this.term = requestEntity.getTerm();
        this.idNode = new dtoIdNode(requestEntity.getGeneralIdNodeEntity());
        this.requestId = requestEntity.getRequestId();
        this.orderId = requestEntity.getOrderId();
        this.priory = requestEntity.getPriory();
        this.time = requestEntity.getTime();
    }
    private Long requestId;
    private dtoCatalog catalog;
    private Long orderId;
    private Long priory;
    private LocalDateTime term;
    private LocalDateTime time;
    private dtoIdNode idNode;
    private Boolean filter;
    private Boolean deleted;


    public dtoIdNode getIdNode() {
        return idNode;
    }

    public void setIdNode(dtoIdNode idNode) {
        this.idNode = idNode;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public dtoCatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(dtoCatalog catalog) {
        this.catalog = catalog;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPriory() {
        return priory;
    }

    public void setPriory(Long priory) {
        this.priory = priory;
    }

    public LocalDateTime getTerm() {
        return term;
    }

    public void setTerm(LocalDateTime term) {
        this.term = term;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Boolean getFilter() {
        return filter;
    }

    public void setFilter(Boolean filter) {
        this.filter = filter;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
