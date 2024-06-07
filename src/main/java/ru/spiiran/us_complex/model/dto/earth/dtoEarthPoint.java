package ru.spiiran.us_complex.model.dto.earth;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class dtoEarthPoint implements IDTOEntity {
    //TODO: возможно будут возникать проблемы с Long - если будет null ошибка падать - заменить на long - примитивный тип данных
    private Long ID;
    private Long idNode;
    private String nameEarthPoint;
    private Double longitude;
    private Double latitude;
    private Boolean isDeleted;

    public dtoEarthPoint() {}

    public dtoEarthPoint(EarthPointEntity earthPointEntity) {
        this.ID = earthPointEntity.getEarthPointId();
        this.nameEarthPoint = earthPointEntity.getNameEarthPoint();
        this.longitude = earthPointEntity.getLongitude();
        this.latitude = earthPointEntity.getLatitude();
        this.isDeleted = false;
        this.idNode = earthPointEntity.getIdNodeEntity().getNodeId();
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}