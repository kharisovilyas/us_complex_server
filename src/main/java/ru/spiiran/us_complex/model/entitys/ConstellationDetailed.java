package ru.spiiran.us_complex.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoDetailedConstellation;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.IEntityNode;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;

@Entity
@Table(name = "constellation_detailed")
public class ConstellationDetailed implements IEntity, IEntityNode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;

    @Column(name = "model_sat")
    private Long modelSat;

    @Column(name = "altitude")
    private Integer altitude;

    @Column(name = "eccentricity")
    private Double eccentricity;

    @Column(name = "incline")
    private Double incline;

    @Column(name = "longitude_ascending_node")
    private Integer longitudeAscendingNode;

    @Column(name = "perigee_width_argument")
    private Double perigeeWidthArgument;

    @Column(name = "true_anomaly")
    private Double trueAnomaly;

    @OneToOne
    @JoinColumn(name = "node_id")
    @JsonIgnore
    private generalIdNodeEntity generalIdNodeEntity;
    @ManyToOne
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private generalStatusEntity generalStatus;
    @ManyToOne
    @JoinColumn(name = "table_id")
    @JsonIgnore
    private ConstellationEntity constellation;


    // Constructors, getters, and setters
    public ConstellationDetailed() {}

    public ConstellationDetailed(dtoDetailedConstellation dtoDetailedConstellation) {
        this.altitude = dtoDetailedConstellation.getAltitude();
        this.incline = dtoDetailedConstellation.getIncline();
        this.eccentricity = dtoDetailedConstellation.getEccentricity();
        this.longitudeAscendingNode = dtoDetailedConstellation.getLongitudeAscendingNode();
        this.perigeeWidthArgument = dtoDetailedConstellation.getPerigeeWidthArgument();
        this.trueAnomaly = dtoDetailedConstellation.getTrueAnomaly();
        this.modelSat = dtoDetailedConstellation.getModelSat();
    }

    public generalIdNodeEntity getGeneralIdNodeEntity() {
        return generalIdNodeEntity;
    }

    public void setGeneralIdNodeEntity(generalIdNodeEntity generalIdNodeEntity) {
        this.generalIdNodeEntity = generalIdNodeEntity;
    }

    public Long getModelSat() {
        return modelSat;
    }

    public void setModelSat(Long modelSat) {
        this.modelSat = modelSat;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Double getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(Double eccentricity) {
        this.eccentricity = eccentricity;
    }

    public Double getIncline() {
        return incline;
    }

    public void setIncline(Double incline) {
        this.incline = incline;
    }
    public Integer getLongitudeAscendingNode() {
        return longitudeAscendingNode;
    }

    public void setLongitudeAscendingNode(Integer longitudeAscendingNode) {
        this.longitudeAscendingNode = longitudeAscendingNode;
    }
    public Double getPerigeeWidthArgument() {
        return perigeeWidthArgument;
    }

    public void setPerigeeWidthArgument(Double perigeeWidthArgument) {
        this.perigeeWidthArgument = perigeeWidthArgument;
    }

    public Double getTrueAnomaly() {
        return trueAnomaly;
    }

    public void setTrueAnomaly(Double trueAnomaly) {
        this.trueAnomaly = trueAnomaly;
    }
    @Override
    public Long getID() {
        return ID;
    }
    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
    public dtoDetailedConstellation getDto() {
        return new dtoDetailedConstellation(this);
    }

    public Long getDetailedConstellationIdNode() {
        return this.generalIdNodeEntity.getIdNode();
    }

    public generalStatusEntity getGeneralStatus() {
        return generalStatus;
    }

    public void setGeneralStatus(generalStatusEntity generalStatus) {
        this.generalStatus = generalStatus;
    }
}