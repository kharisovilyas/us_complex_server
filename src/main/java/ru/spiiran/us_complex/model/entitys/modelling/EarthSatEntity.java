package ru.spiiran.us_complex.model.entitys.modelling;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "earth_sat")
public class EarthSatEntity implements IEntity {
    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return null;
    }

    @Id
    @Column(name = "id_earth_sat")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long earthSatId;
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

    @Column(name = "begin_time")
    private String beginTime;

    @Column(name = "end_time")
    private String endTime;

    public EarthSatEntity() {}

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

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
