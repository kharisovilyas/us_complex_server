package ru.spiiran.us_complex.model.entitys.modelling;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoEarthSat;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "earth_sat")
public class EarthSatEntity implements IEntity {
    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    @Id
    @Column(name = "id_earth_sat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long earthSatId;

    @Column(name = "begin_time")
    private Long beginTime;

    @Column(name = "end_time")
    private Long endTime;

    public EarthSatEntity(dtoEarthSat earthSat) {
        this.beginTime = earthSat.getBegin();
    }

    public EarthSatEntity() {}

    public Long getEarthSatId() {
        return earthSatId;
    }

    public void setEarthSatId(Long earthSatId) {
        this.earthSatId = earthSatId;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /*
    @OneToMany
    @JoinColumn(name = "id_node")
    private List<IdNodeEntity> idNodeEntityList;

    @OneToMany
    @JoinColumn(name = "id_arbitrary_satellite")
    private List<coArbitraryConstruction> arbitraryConstructionList;

    /*@OneToMany
    @JoinColumn(name = "id_planar_satellite")
    private coPlanarConstruction planarConstruction;

    public List<IdNodeEntity> getIdNodeEntityList() {
        return idNodeEntityList;
    }

    public void setIdNodeEntityList(List<IdNodeEntity> idNodeEntityList) {
        this.idNodeEntityList = idNodeEntityList;
    }

    public List<coArbitraryConstruction> getArbitraryConstructionList() {
        return arbitraryConstructionList;
    }

    public void setArbitraryConstructionList(List<coArbitraryConstruction> arbitraryConstructionList) {
        this.arbitraryConstructionList = arbitraryConstructionList;
    }

    public Long getEarthSatId() {
        return earthSatId;
    }

    public void setEarthSatId(Long earthSatId) {
        this.earthSatId = earthSatId;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return null;
    }
    */
}
