package ru.spiiran.us_complex.model.entitys.constellation;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.spiiran.us_complex.model.dto.constellation.dtoSatellite;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;
import ru.spiiran.us_complex.model.entitys.modelling.EarthSatEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;
import ru.spiiran.us_complex.repositories.NodeIdRepository;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "satellite")
public class SatelliteEntity implements IEntity {
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
    @Column(name = "phase_shift")
    private Double phaseShift;
    @Column(name = "position")
    private Long position;
    @Column(name = "plane")
    private Long plane;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_node")
    private IdNodeEntity idNodeEntity;
    @ManyToOne
    @JoinColumn(name = "id_constellation")
    private ConstellationEntity constellation;
    @ManyToOne
    @JoinColumn(name = "id_model_sat")
    private ModelSatEntity modelSat;
    @OneToMany(mappedBy = "satellite", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<EarthSatEntity> earthSatContacts = new ArrayList<>();

    public SatelliteEntity() {
    }

    public SatelliteEntity(
            SatelliteEntity existingSatellite,
            dtoSatellite dtoSatellite,
            ConstellationEntity existingConstellation,
            NodeIdRepository nodeIdRepository,
            IdNodeEntity newConstellationNode
    ) {
        this.altitude = dtoSatellite.getAltitude();
        //TODO: пока не работает, при спринте доделать - МОДЕЛЬ КА
        // this.modelSat = dtoSatellite.getModelSat();
        this.incline = dtoSatellite.getIncline();
        this.eccentricity = dtoSatellite.getEccentricity();
        this.longitudeAscendingNode = dtoSatellite.getLongitudeAscendingNode();
        this.perigeeWidthArgument = dtoSatellite.getPerigeeWidthArgument();
        this.trueAnomaly = dtoSatellite.getTrueAnomaly();
        if (existingSatellite != null) {
            this.idNodeEntity = existingSatellite.getIdNodeEntity();
            //TODO: если новое имя для ОГ то менять idNodeEntity
            this.constellation = existingSatellite.getConstellation();
            this.satelliteId = existingSatellite.getSatelliteId();
        } else {
            this.idNodeEntity = newConstellationNode;
            this.idNodeEntity = nodeIdRepository.save(this.idNodeEntity);
            this.constellation = existingConstellation;
        }
    }

    public SatelliteEntity(dtoSatellite satellite) {
        this.altitude = satellite.getAltitude();
        this.eccentricity = satellite.getEccentricity();
        this.incline = satellite.getIncline();
        this.longitudeAscendingNode = satellite.getLongitudeAscendingNode();
        this.perigeeWidthArgument = satellite.getPerigeeWidthArgument();
        this.trueAnomaly = satellite.getTrueAnomaly();
        this.phaseShift = satellite.getPhaseShift();
        this.position = satellite.getPosition();
        this.plane = satellite.getPlane();
    }

    public SatelliteEntity(
            String nodeType,
            Long maxIdNode,
            ConstellationEntity newConstellation,
            dtoSatellite dtoSatellite,
            NodeIdRepository nodeIdRepository
    ) {
        this.altitude = dtoSatellite.getAltitude();
        //TODO: пока не работает, при спринте доделать - МОДЕЛЬ КА
        // this.modelSat = dtoSatellite.getModelSat();
        this.incline = dtoSatellite.getIncline();
        this.eccentricity = dtoSatellite.getEccentricity();
        this.longitudeAscendingNode = dtoSatellite.getLongitudeAscendingNode();
        this.perigeeWidthArgument = dtoSatellite.getPerigeeWidthArgument();
        this.trueAnomaly = dtoSatellite.getTrueAnomaly();
        this.idNodeEntity = new IdNodeEntity(maxIdNode, nodeType);
        this.idNodeEntity = nodeIdRepository.save(this.idNodeEntity);
        this.constellation = newConstellation;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
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

    public Double getLongitudeAscendingNode() {
        return longitudeAscendingNode;
    }

    public void setLongitudeAscendingNode(Double longitudeAscendingNode) {
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

    public Double getPhaseShift() {
        return phaseShift;
    }

    public void setPhaseShift(Double phaseShift) {
        this.phaseShift = phaseShift;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

    public Long getPlane() {
        return plane;
    }

    public void setPlane(Long plane) {
        this.plane = plane;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public IdNodeEntity getIdNodeEntity() {
        return idNodeEntity;
    }

    public void setIdNodeEntity(IdNodeEntity idNodeEntity) {
        this.idNodeEntity = idNodeEntity;
    }

    public ConstellationEntity getConstellation() {
        return constellation;
    }

    public void setConstellation(ConstellationEntity constellation) {
        this.constellation = constellation;
    }

    public ModelSatEntity getModelSat() {
        return modelSat;
    }

    public void setModelSat(ModelSatEntity modelSat) {
        this.modelSat = modelSat;
    }

    public List<EarthSatEntity> getEarthSatContacts() {
        return earthSatContacts;
    }

    public void setEarthSatContacts(List<EarthSatEntity> earthSatContacts) {
        this.earthSatContacts = earthSatContacts;
    }
}
