package ru.spiiran.us_complex.model.entitys.modelsat.techparam;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.techparam.dtoTechParameter;
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
    private List<msParameterEntity> parameterEntityList;

    @OneToMany(mappedBy = "msTechParamEntity")
    private List<ModelSatEntity> modelSatEntityList;

    @Column(name = "value_of_prm")
    private Double valueOfPrm;

    public void setID(Long ID) {
        this.ID = ID;
    }

    public List<msParameterEntity> getListOfParamEntities() {
        return parameterEntityList;
    }

    public msTechParamEntity(dtoTechParameter dtoTechParam) {
        this.valueOfPrm = dtoTechParam.getValueOfPrm();
    }

    public void setListOfParamEntities(List<msParameterEntity> listOfParamEntities) {
        this.parameterEntityList = listOfParamEntities;
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
