package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.IEntityNode;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;

@Entity
@Table(name= "earth_point")
public class EarthPointEntity implements IEntity, IEntityNode {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String nameEarthPoint;
    private Double longitude;
    private Double latitude;
    @OneToOne
    @JoinColumn(name = "status_id")
    private generalStatusEntity generalStatusEntity;
    @OneToOne
    @JoinColumn(name = "node_id")
    private generalIdNodeEntity generalIdNodeEntity;

    public EarthPointEntity(dtoEarthPoint earthPoint) {
        this.nameEarthPoint = earthPoint.getNameEarthPoint();
        this.longitude = earthPoint.getLongitude();
        this.latitude = earthPoint.getLatitude();
    }

    public EarthPointEntity() {}

    public generalIdNodeEntity getGeneralIdNodeEntity() {
        return generalIdNodeEntity;
    }

    public void setGeneralIdNodeEntity(generalIdNodeEntity generalIdNodeEntity) {
        this.generalIdNodeEntity = generalIdNodeEntity;
    }

    public generalStatusEntity getStatusGeneral() {
        return generalStatusEntity;
    }

    public dtoEarthPoint getDto() {
        return new dtoEarthPoint(this);
    }

    @Override
    public long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
    public void setID(long ID) {
        this.ID = ID;
    }

    @Override
    public Long getIdNode() {
        return generalIdNodeEntity.getIdNode();
    }

    public String getNameEarthPoint() {
        return nameEarthPoint;
    }

    public void setNameEarthPoint(String name_earth_point) {
        this.nameEarthPoint = name_earth_point;
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

    public void setStatusGeneral(generalStatusEntity generalStatusEntity) {
        this.generalStatusEntity = generalStatusEntity;
    }
}
