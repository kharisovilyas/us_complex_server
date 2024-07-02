package ru.spiiran.us_complex.model.dto.satrequest;

import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.entitys.satrequest.RequestEntity;

public class dtoRequest {
    public dtoRequest() {
    }
    public dtoRequest(RequestEntity requestEntity) {
        this.catalog = new dtoCatalog(requestEntity.getCatalogEntity());
        this.filter = requestEntity.getFilter();
        this.term = requestEntity.getTerm();
        this.earthPoint = new dtoEarthPoint(requestEntity.getEarthPoint());
        this.requestId = requestEntity.getRequestId();
        this.orderId = requestEntity.getOrderId();
        this.priory = requestEntity.getPriory();
        this.time = requestEntity.getTime();
    }
    private Long requestId;
    private dtoCatalog catalog;
    private Long orderId;
    private Long priory;
    private Long term;
    private Long time;
    private dtoEarthPoint earthPoint;
    private Boolean filter;
    private Boolean deleted;

    public dtoEarthPoint getEarthPoint() {
        return earthPoint;
    }

    public void setEarthPoint(dtoEarthPoint earthPoint) {
        this.earthPoint = earthPoint;
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

    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
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
