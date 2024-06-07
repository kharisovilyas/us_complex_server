package ru.spiiran.us_complex.model.entitys.constellation;

import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "constellation")
    private List<coArbitraryConstruction> coArbitraryConstructionList;
    @OneToMany(mappedBy = "constellation", cascade = CascadeType.ALL)
    private List<coPlanarConstruction> coPlanarConstructionList;

    public ConstellationEntity(){}

    public ConstellationEntity(dtoConstellation dtoConstellation) {
        this.isArbitraryFormation = dtoConstellation.getArbitraryFormation();
        this.constellationName = dtoConstellation.getConstellationName();
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

    public List<coArbitraryConstruction> getArbitraryConstructionList() {
        return coArbitraryConstructionList;
    }

    public void setArbitraryConstructionList(List<coArbitraryConstruction> coArbitraryConstructions) {
        this.coArbitraryConstructionList = coArbitraryConstructions;
    }

    public List<coPlanarConstruction> getPlanarConstructionList() {
        return coPlanarConstructionList;
    }

    public void setPlanarConstructionList(List<coPlanarConstruction> coPlanarConstructionList) {
        this.coPlanarConstructionList = coPlanarConstructionList;
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
}
