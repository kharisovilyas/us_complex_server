package ru.spiiran.us_complex.model.entitys.constellation;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.constellation.dtoArbitraryConstruction;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;

@Entity
@Table(name = "co_arbitrary_construction")
public class coArbitraryConstruction implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_satellite")
    private Long satelliteId;
    @Column(name = "altitude")
    private Double altitude;
    @Column(name = "eccentricity")
    private Double eccentricity;
    @Column(name = "incline")
    private Double incline;
    @Column(name = "longitude_ascending_node")
    private Double longitudeAscendingNode;
    @Column(name = "perigee_width_argument")
    private Double perigeeWidthArgument;
    @Column(name = "true_anomaly")
    private Double trueAnomaly;
    @OneToOne
    @JoinColumn(name = "node_id")
    private IdNodeEntity idNodeEntity;
    @ManyToOne
    @JoinColumn(name = "constellation_id")
    private ConstellationEntity constellation;
    @Column(name="model_sat_id")
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

    public coArbitraryConstruction(
            coArbitraryConstruction existingConstellation,
            dtoArbitraryConstruction dtoArbitraryConstruction
    ) {
        this.satelliteId = existingConstellation.getSatelliteId();
        this.altitude = dtoArbitraryConstruction.getAltitude();
        this.modelSat = dtoArbitraryConstruction.getModelSat();
        this.incline = dtoArbitraryConstruction.getIncline();
        this.eccentricity = dtoArbitraryConstruction.getEccentricity();
        this.longitudeAscendingNode = dtoArbitraryConstruction.getLongitudeAscendingNode();
        this.perigeeWidthArgument = dtoArbitraryConstruction.getPerigeeWidthArgument();
        this.trueAnomaly = dtoArbitraryConstruction.getTrueAnomaly();
        if(existingConstellation.idNodeEntity != null) {
            this.idNodeEntity = existingConstellation.idNodeEntity;
        }
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
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
    public dtoArbitraryConstruction getDto() {
        return new dtoArbitraryConstruction(this);
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

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public Double getLongitudeAscendingNode() {
        return longitudeAscendingNode;
    }

    public void setLongitudeAscendingNode(Double longitudeAscendingNode) {
        this.longitudeAscendingNode = longitudeAscendingNode;
    }

    public IdNodeEntity getIdNodeEntity() {
        return idNodeEntity;
    }

    public void setIdNodeEntity(IdNodeEntity idNodeEntity) {
        this.idNodeEntity = idNodeEntity;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }
}