package ru.spiiran.us_complex.model.entitys.modelsat;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.dtoOperatingParameter;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "operating_parameters")
public class msOperatingParameter implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_op_param")
    private Long operationParamId;

    @Column(name = "parameter_name")
    private String parameterName;

    @Column(name = "value")
    private Double value;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_model_sat")
    private ModelSatEntity modelSatEntity;

    // Конструктор
    public msOperatingParameter() {}

    public msOperatingParameter(dtoOperatingParameter operatingParameter) {
        this.operationParamId = operatingParameter.getOperationParamId();
        this.parameterName = operatingParameter.getParameterName();
        this.value = operatingParameter.getValue();
    }

    // Геттеры и сеттеры
    public Long getOperationParamId() {
        return operationParamId;
    }

    public void setOperationParamId(Long operationParamId) {
        this.operationParamId = operationParamId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}