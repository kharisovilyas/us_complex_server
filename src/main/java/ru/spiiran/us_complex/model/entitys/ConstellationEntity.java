package ru.spiiran.us_complex.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;

import java.util.List;

@Entity
@Table(name = "constellation")
public class ConstellationEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;
    @Column(name = "constellation_name")
    private String constellationName;
    @Column(name = "arbitrary_formation")
    private Boolean isArbitraryFormation;

    @Column(name="model_sat")
    private Long modelSat;
    @OneToMany(mappedBy = "constellation")
    private List<ConstellationArbitrary> constellationArbitraryList;
    @OneToMany(mappedBy = "constellation", cascade = CascadeType.ALL)
    private List<ConstellationPlanar> constellationPlanarList;
    @ManyToOne //TODO: данное каскадное взаимодействие - может удалять записи таблицы, из-за связей многие ко многим, аккуратно использовать их!
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private generalStatusEntity generalStatus;

    public ConstellationEntity(){}

    public ConstellationEntity(dtoConstellation dtoConstellation) {
        this.isArbitraryFormation = dtoConstellation.getArbitraryFormation();
        this.constellationName = dtoConstellation.getConstellationName();
        this.modelSat = dtoConstellation.getModelSat();
    }
    public Boolean getArbitraryFormation() {
        return isArbitraryFormation;
    }

    public void setArbitraryFormation(Boolean arbitraryFormation) {
        isArbitraryFormation = arbitraryFormation;
    }

    public Long getModelSat() {
        return modelSat;
    }

    public void setModelSat(Long modelSat) {
        this.modelSat = modelSat;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    public List<ConstellationArbitrary> getConstellationArbitraryList() {
        return constellationArbitraryList;
    }

    public void setConstellationArbitraryList(List<ConstellationArbitrary> constellationArbitraryList) {
        this.constellationArbitraryList = constellationArbitraryList;
    }

    public List<ConstellationPlanar> getConstellationPlanarList() {
        return constellationPlanarList;
    }

    public void setConstellationPlanarList(List<ConstellationPlanar> constellationPlanarList) {
        this.constellationPlanarList = constellationPlanarList;
    }

    public generalStatusEntity getGeneralStatus() {
        return generalStatus;
    }

    public void setGeneralStatus(generalStatusEntity generalStatus) {
        this.generalStatus = generalStatus;
    }

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
