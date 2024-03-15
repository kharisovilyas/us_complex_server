package ru.spiiran.us_complex.model.entitys.modelsat.techparam;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;

import java.util.List;

@Entity
@Table(name="ms_tech_param")
public class msTechParamEntity implements IEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @OneToMany(mappedBy = "msTechParamEntity")
    private List<msListOfParam> listOfParams;

    @OneToMany(mappedBy = "msTechParamEntity")
    private List<ModelSatEntity> modelSatEntityList;

    @Column(name = "value_of_prm")
    private Double valueOfPrm;

    public void setID(Long ID) {
        this.ID = ID;
    }

    public List<msListOfParam> getListOfParams() {
        return listOfParams;
    }

    public void setListOfParams(List<msListOfParam> listOfParams) {
        this.listOfParams = listOfParams;
    }

    public List<ModelSatEntity> getTableBList() {
        return modelSatEntityList;
    }

    public void setTableBList(List<ModelSatEntity> tableBList) {
        this.modelSatEntityList = tableBList;
    }

    public Double getValueOfPrm() {
        return valueOfPrm;
    }

    public void setValueOfPrm(Double valueOfPrm) {
        this.valueOfPrm = valueOfPrm;
    }

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
