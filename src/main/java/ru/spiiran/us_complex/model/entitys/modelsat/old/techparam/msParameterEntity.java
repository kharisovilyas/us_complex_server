package ru.spiiran.us_complex.model.entitys.modelsat.old.techparam;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.old.techparam.dtoParameter;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_list_of_param")
public class msParameterEntity implements IEntity {
    @Id
    @Column(name = "prm_id")
    private Long prmId;
    @Column(name = "parameter")
    private String parameter;
    @ManyToOne
    @JoinColumn(name = "tech_param_id")
    private msTechParamEntity msTechParamEntity;

    public msParameterEntity() {
    }

    public msParameterEntity(dtoParameter dto) {
        this.prmId = dto.getPrmId();
        this.parameter = dto.getParameter();
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ru.spiiran.us_complex.model.entitys.modelsat.old.techparam.msTechParamEntity getMsTechParamEntity() {
        return msTechParamEntity;
    }

    public void setMsTechParamEntity(ru.spiiran.us_complex.model.entitys.modelsat.old.techparam.msTechParamEntity msTechParamEntity) {
        this.msTechParamEntity = msTechParamEntity;
    }

    public Long getPrmId() {
        return prmId;
    }

    public void setPrmId(Long prmId) {
        this.prmId = prmId;
    }
}
