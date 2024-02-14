package ru.spiiran.us_complex.model.entitys.general;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.EarthPointEntity;

@Entity
@Table(name = "id_node_general")
public class generalIdNodeEntity implements IEntity, IEntityNode{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;

    @Column(name = "id_node")
    private Long idNode;

    @OneToOne(mappedBy = "generalIdNodeEntity", cascade = CascadeType.ALL)
    private EarthPointEntity earthPointEntity;

    @Override
    public long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    @Override
    public Long getIdNode() {
        return idNode;
    }

    public void setIdNode(Long idNode) {
        this.idNode = idNode;
    }
}
