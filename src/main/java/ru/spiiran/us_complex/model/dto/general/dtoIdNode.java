package ru.spiiran.us_complex.model.dto.general;

import ru.spiiran.us_complex.model.entitys.general.generalIdNodeEntity;

public class dtoIdNode {
    private Long entryId;
    private Long idNode;
    private String nodeType;

    public dtoIdNode(generalIdNodeEntity generalIdNode) {
        this.idNode = generalIdNode.getIdNode();
        this.entryId = generalIdNode.getID();
        this.nodeType = generalIdNode.getNodeType();
    }

    public dtoIdNode() {
    }

    public Long getIdNode() {
        return idNode;
    }

    public void setIdNode(Long idNode) {
        this.idNode = idNode;
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
}
