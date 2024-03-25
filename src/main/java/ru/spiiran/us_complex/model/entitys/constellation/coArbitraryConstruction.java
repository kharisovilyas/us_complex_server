package ru.spiiran.us_complex.model.entitys.constellation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoArbitraryConstruction;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.IEntityNode;
import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;

@Entity
@Table(name = "co_arbitrary_construction")
public class coArbitraryConstruction implements IEntity, IEntityNode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;

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
    @JoinColumn(name = "table_id")
    private ConstellationEntity constellation;
    @Column(name="model_sat")
    private Long modelSat;


    // Constructors, getters, and setters
    public coArbitraryConstruction() {}

    public coArbitraryConstruction(dtoArbitraryConstruction dtoArbitraryConstruction) {
        this.altitude = dtoArbitraryConstruction.getAltitude();
        this.modelSat = dtoArbitraryConstruction.getModelSat();
        this.incline = dtoArbitraryConstruction.getIncline();
        this.eccentricity = dtoArbitraryConstruction.getEccentricity();
        this.longitudeAscendingNode = dtoArbitraryConstruction.getLongitudeAscendingNode();
        this.perigeeWidthArgument = dtoArbitraryConstruction.getPerigeeWidthArgument();
        this.trueAnomaly = dtoArbitraryConstruction.getTrueAnomaly();
    }

    public generalIdNodeEntity getGeneralIdNodeEntity() {
        return generalIdNodeEntity;
    }

    public void setGeneralIdNodeEntity(generalIdNodeEntity generalIdNodeEntity) {
        this.generalIdNodeEntity = generalIdNodeEntity;
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
    public dtoArbitraryConstruction getDto() {
        return new dtoArbitraryConstruction(this);
    }

    public Long getArbitraryConstructionIdNode() {
        return this.generalIdNodeEntity.getIdNode();
    }

    public ConstellationEntity getConstellation() {
        return constellation;
    }

    public void setConstellation(ConstellationEntity constellation) {
        this.constellation = constellation;
    }

    public Long getModelSat() {
        return modelSat;
    }

    public void setModelSat(Long modelSat) {
        this.modelSat = modelSat;
    }
}