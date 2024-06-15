package ru.spiiran.us_complex.model.entitys.constellation;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.util.List;

@Entity
@Table(name = "constellation")
public class ConstellationEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_constellation")
    private Long constellationId;
    @Column(name = "constellation_name")
    private String constellationName;
    @Column(name = "arbitrary_formation")
    private Boolean isArbitraryFormation;
    @OneToMany(mappedBy = "constellation", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<SatelliteEntity> satelliteEntities;

    public ConstellationEntity(ConstellationEntity constellation){
        this.constellationId = constellation.getConstellationId();
        this.constellationName = constellation.getConstellationName();
        this.isArbitraryFormation = constellation.getArbitraryFormation();
        this.satelliteEntities = constellation.getSatelliteEntities();
    }

    public ConstellationEntity(){}

    public ConstellationEntity(dtoConstellation dtoConstellation) {
        this.isArbitraryFormation = dtoConstellation.getArbitraryFormation();
        this.constellationName = dtoConstellation.getConstellationName();
    }

    public ConstellationEntity(dtoConstellation dtoConstellation, ConstellationEntity constellation) {
        this.constellationId = constellation.getConstellationId();
        this.constellationName = dtoConstellation.getConstellationName();
        this.isArbitraryFormation = //TODO: возможно потом добавить возможность обновлять список КА в группировке в зависимости от вида ее построения
                constellation.getArbitraryFormation();
        this.satelliteEntities = constellation.getSatelliteEntities();
    }

    public Boolean getArbitraryFormation() {
        return isArbitraryFormation;
    }

    public void setArbitraryFormation(Boolean arbitraryFormation) {
        isArbitraryFormation = arbitraryFormation;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public Long getConstellationId() {
        return constellationId;
    }

    public void setConstellationId(Long constellationId) {
        this.constellationId = constellationId;
    }

    public List<SatelliteEntity> getSatelliteEntities() {
        return satelliteEntities;
    }

    public void setSatelliteEntities(List<SatelliteEntity> satelliteEntities) {
        this.satelliteEntities = satelliteEntities;
    }
}
