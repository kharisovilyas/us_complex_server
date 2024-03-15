package ru.spiiran.us_complex.model.entitys.earth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;

@Entity
@Table(name= "earth_point")
public class EarthPointEntity implements IEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String nameEarthPoint;
    private Double longitude;
    private Double latitude;

    @OneToOne
    @JoinColumn(name = "node_id")
    @JsonIgnore
    private generalIdNodeEntity generalIdNodeEntity;

    @ManyToOne
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private generalStatusEntity generalStatus;

    public EarthPointEntity(dtoEarthPoint earthPoint) {
        this.nameEarthPoint = earthPoint.getNameEarthPoint();
        this.longitude = earthPoint.getLongitude();
        this.latitude = earthPoint.getLatitude();
    }
    public EarthPointEntity() {}

    public generalIdNodeEntity getGeneralIdNodeEntity() {
        return generalIdNodeEntity;
    }

    public dtoEarthPoint getDto() {
        return new dtoEarthPoint(this);
    }

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
    public void setID(long ID) {
        this.ID = ID;
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

    public Long getEarthIdNode() {
        return generalIdNodeEntity.getIdNode();
    }
    public void setGeneralIdNodeEntity(generalIdNodeEntity generalIdNodeEntity) {
        this.generalIdNodeEntity = generalIdNodeEntity;
    }

    public generalStatusEntity getGeneralStatus() {
        return generalStatus;
    }

    public void setGeneralStatus(generalStatusEntity generalStatus) {
        this.generalStatus = generalStatus;
    }
}
