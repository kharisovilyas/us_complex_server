package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

import ru.spiiran.us_complex.model.dto.satrequest.dtoCatalog;
import ru.spiiran.us_complex.model.dto.satrequest.dtoRequest;
import ru.spiiran.us_complex.model.entitys.satrequest.CatalogEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.RequestEntity;

public class Order {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Long priority;
    private Long tf;

    public Order() {
    }

    public Order(dtoRequest dtoRequest) {
        dtoCatalog dtoCatalog = dtoRequest.getCatalog();
        this.id = dtoCatalog.getGoalId();
        this.name = dtoCatalog.getGoalName();
        this.latitude = dtoCatalog.getAlt();
        this.longitude = dtoCatalog.getLon();
        this.altitude = dtoCatalog.getAlt();
        this.priority = dtoRequest.getPriory();
        this.tf = dtoRequest.getTerm();
    }

    public Order(RequestEntity request) {
        CatalogEntity catalogEntity = request.getCatalogEntity();
        this.id = catalogEntity.getGoalId();
        this.name = catalogEntity.getGoalName();
        this.latitude = catalogEntity.getLat();
        this.longitude = catalogEntity.getLon();
        this.altitude = catalogEntity.getAlt();
        this.priority = request.getPriory();
        this.tf = request.getTerm();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getTf() {
        return tf;
    }

    public void setTf(Long tf) {
        this.tf = tf;
    }
}
