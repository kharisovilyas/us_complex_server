package ru.spiiran.us_complex.model.entitys.general;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;

@Entity
@Table(name = "id_node")
public class IdNodeEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_entry")
    private Long entryId;

    @Column(name = "node_type")
    private String nodeType;

    @Column(name = "id_node")
    private Long nodeId;

    @OneToOne(mappedBy = "idNodeEntity", cascade = CascadeType.ALL)
    private EarthPointEntity earthPointEntity;

    @OneToOne(mappedBy = "idNodeEntity")
    @JoinColumn(name = "id_satellite")
    private SatelliteEntity satellite;

    public IdNodeEntity(Long maxEarthNode, String nodeType) {
        this.nodeId = maxEarthNode + 1;
        this.nodeType = nodeType;
    }

    public IdNodeEntity() {
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public EarthPointEntity getEarthPointEntity() {
        return earthPointEntity;
    }

    public void setEarthPointEntity(EarthPointEntity earthPointEntity) {
        this.earthPointEntity = earthPointEntity;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public SatelliteEntity getSatellite() {
        return satellite;
    }

    public void setSatellite(SatelliteEntity satellite) {
        this.satellite = satellite;
    }
}
