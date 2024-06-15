package ru.spiiran.us_complex.model.entitys.earth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.spiiran.us_complex.model.dto.earth.dtoEarthPoint;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.model.entitys.modelling.EarthSatEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "earth_point")
public class EarthPointEntity implements IEntity {
    @Id
    @Column(name = "id_earth_point")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long earthPointId;
    @Column(name = "name_earth_point")
    private String nameEarthPoint;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "latitude")
    private Double latitude;
    @OneToMany(mappedBy = "earthPoint", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<EarthSatEntity> earthSatContacts = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "node_id")
    @JsonIgnore
    private IdNodeEntity idNodeEntity;


    public EarthPointEntity(dtoEarthPoint earthPoint) {
        this.nameEarthPoint = earthPoint.getNameEarthPoint();
        this.longitude = earthPoint.getLongitude();
        this.latitude = earthPoint.getLatitude();
    }

    public EarthPointEntity(EarthPointEntity earthPointEntity, dtoEarthPoint dtoEarthPoint) {
        this.earthPointId = earthPointEntity.getEarthPointId();
        this.nameEarthPoint = dtoEarthPoint.getNameEarthPoint();
        this.longitude = dtoEarthPoint.getLongitude();
        this.latitude = dtoEarthPoint.getLatitude();
        if (earthPointEntity.idNodeEntity != null) {
            this.idNodeEntity = earthPointEntity.idNodeEntity;
        }
    }

    public EarthPointEntity() {}

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
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

    public IdNodeEntity getIdNodeEntity() {
        return idNodeEntity;
    }

    public void setIdNodeEntity(IdNodeEntity idNodeEntity) {
        this.idNodeEntity = idNodeEntity;
    }

    public Long getEarthPointId() {
        return earthPointId;
    }

    public void setEarthPointId(Long earthPointId) {
        this.earthPointId = earthPointId;
    }

    public List<EarthSatEntity> getEarthSatContacts() {
        return earthSatContacts;
    }

    public void setEarthSatContacts(List<EarthSatEntity> earthSatContacts) {
        this.earthSatContacts = earthSatContacts;
    }
}