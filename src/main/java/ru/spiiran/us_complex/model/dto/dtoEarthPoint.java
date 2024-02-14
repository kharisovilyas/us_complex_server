package ru.spiiran.us_complex.model.dto;

import ru.spiiran.us_complex.model.entitys.EarthPointEntity;

public class dtoEarthPoint implements IDTOEntity {
    private Long ID;
    private Long idNode;
    private String nameEarthPoint;
    private Double longitude;
    private Double latitude;
    private Boolean statusOfEdit;

    public dtoEarthPoint() {}

    public dtoEarthPoint(EarthPointEntity earthPointEntity) {
        this.ID = earthPointEntity.getID();
        this.nameEarthPoint = earthPointEntity.getNameEarthPoint();
        this.longitude = earthPointEntity.getLongitude();
        this.latitude = earthPointEntity.getLatitude();
        this.statusOfEdit = earthPointEntity.getStatusGeneral() != null && earthPointEntity.getStatusGeneral().getStatusOfEdit() != null
                ? earthPointEntity.getStatusGeneral().getStatusOfEdit()
                : false;
        this.idNode = earthPointEntity.getGeneralIdNodeEntity().getIdNode();
    }

    public Long getIdNode() {
        return idNode;
    }

    public void setIdNode(Long idNode) {
        this.idNode = idNode;
    }

    public String getNameEarthPoint() {
        return nameEarthPoint;
    }

    public void setNameEarthPoint(String nameEarthPoint) {
        this.nameEarthPoint = nameEarthPoint;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Boolean getStatusOfEdit() {
        return statusOfEdit;
    }

    public void setStatusOfEdit(Boolean statusOfEdit) {
        this.statusOfEdit = statusOfEdit;
    }
}
