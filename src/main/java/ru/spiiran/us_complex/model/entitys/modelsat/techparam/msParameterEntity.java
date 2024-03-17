package ru.spiiran.us_complex.model.entitys.modelsat.techparam;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.techparam.dtoParameter;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_list_of_param")
public class msParameterEntity implements IEntity {
    @Id
    @Column(name = "prm_id")
    private Long prmID;
    @Column(name = "parameter")
    private String parameter;
    @ManyToOne
    @JoinColumn(name = "tech_param_id")
    private msTechParamEntity msTechParamEntity;

    public msParameterEntity() {
    }

    public msParameterEntity(dtoParameter dto) {
        this.prmID = dto.getPrmId();
        this.parameter = dto.getParameter();
    }

    @Override
    public Long getID() {
        return prmID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
