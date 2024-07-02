package ru.spiiran.us_complex.model.dto.modelsat;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.msOperatingParameter;

public class dtoOperatingParameter implements IDTOEntity {
    private Long operationParamId;
    private String parameterName;
    private Double value;

    public dtoOperatingParameter() {
    }

    public dtoOperatingParameter(msOperatingParameter operatingParameters) {
        this.operationParamId = operatingParameters.getOperationParamId();
        this.parameterName = operatingParameters.getParameterName();
        this.value = operatingParameters.getValue();
    }

    public dtoOperatingParameter(Long operationParamId, String parameterName, Double value) {
        this.operationParamId = operationParamId;
        this.parameterName = parameterName;
        this.value = value;
    }

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
}
