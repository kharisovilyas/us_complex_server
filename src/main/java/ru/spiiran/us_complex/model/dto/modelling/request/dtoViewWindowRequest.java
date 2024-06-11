package ru.spiiran.us_complex.model.dto.modelling.request;

import ru.spiiran.us_complex.model.dto.constellation.dtoConstellation;

public class dtoViewWindowRequest {

    private Long startTime;
    private Long modellingBegin;
    private Long modellingEnd;
    private Double modellingStep;
    private dtoConstellation constellation;

    public Long getModellingBegin() {
        return modellingBegin;
    }

    public void setModellingBegin(Long modellingBegin) {
        this.modellingBegin = modellingBegin;
    }

    public Long getModellingEnd() {
        return modellingEnd;
    }

    public void setModellingEnd(Long modellingEnd) {
        this.modellingEnd = modellingEnd;
    }

    public Double getModellingStep() {
        return modellingStep;
    }

    public void setModellingStep(Double modellingStep) {
        this.modellingStep = modellingStep;
    }

    public dtoConstellation getConstellation() {
        return constellation;
    }

    public void setConstellation(dtoConstellation constellation) {
        this.constellation = constellation;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
