package ru.spiiran.us_complex.model.entitys.general;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;

@Entity
@Table(name = "id_node")
public class IdNodeEntity {
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

    @OneToOne(mappedBy = "idNodeEntity", cascade = CascadeType.ALL)
    private coArbitraryConstruction arbitraryConstruction;

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

    public coArbitraryConstruction getArbitraryConstruction() {
        return arbitraryConstruction;
    }

    public void setArbitraryConstruction(coArbitraryConstruction arbitraryConstruction) {
        this.arbitraryConstruction = arbitraryConstruction;
    }
}
