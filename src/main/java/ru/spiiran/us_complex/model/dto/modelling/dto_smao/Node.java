package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

import ru.spiiran.us_complex.model.dto.general.dtoIdNode;
import ru.spiiran.us_complex.model.entitys.general.IdNodeEntity;

public class Node {
    private Integer id;
    private String typeName;
    public Node(dtoIdNode dtoIdNode){
        //TODO: быть внимательным вдруг idNode выйдет за пределы int, предложить КАЮ поменять поле
        this.id = Integer.parseInt(dtoIdNode.getIdNode().toString());
        this.typeName = dtoIdNode.getNodeType();
    }

    public Node(IdNodeEntity idNodeEntity){
        //TODO: быть внимательным вдруг idNode выйдет за пределы int, предложить КАЮ поменять поле
        this.id = Integer.parseInt(idNodeEntity.getNodeId().toString());
        this.typeName = idNodeEntity.getNodeType();
    }

    public Node() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
