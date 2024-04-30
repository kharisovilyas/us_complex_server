package ru.spiiran.us_complex.model.entitys.general;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.coArbitraryConstruction;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.satrequest.RequestEntity;

import java.util.List;

@Entity
@Table(name = "id_node_general")
public class generalIdNodeEntity implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;

    @Column(name = "id_node")
    private Long idNode;

    @OneToOne(mappedBy = "generalIdNodeEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private EarthPointEntity earthPointEntity;

    @OneToOne(mappedBy = "generalIdNodeEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private coArbitraryConstruction coArbitraryConstruction;

    @OneToMany(mappedBy = "generalIdNodeEntity", cascade = CascadeType.ALL)
    private List<RequestEntity> requestEntityList;

    public generalIdNodeEntity(Long idNode) {
        this.idNode = idNode;
    }

    public generalIdNodeEntity() {
    }

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public Long getIdNode() {
        return idNode;
    }

    public void setIdNode(Long idNode) {
        this.idNode = idNode;
    }
}