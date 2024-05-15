package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class Parameters {
    Long t0;
    Long modelingBegin;
    Long modelingEnd;
    Long modelingTimeStep;
    Boolean isInterSatelliteCommunication;
    String controlSystem;
    Long duration;

    public Parameters(Long t0, Long modelingBegin, Long modelingEnd, Long modelingTimeStep, Boolean isInterSatelliteCommunication, String controlSystem, Long duration) {
        this.t0 = t0;
        this.modelingBegin = modelingBegin;
        this.modelingEnd = modelingEnd;
        this.modelingTimeStep = modelingTimeStep;
        this.isInterSatelliteCommunication = isInterSatelliteCommunication;
        this.controlSystem = controlSystem;
        this.duration = duration;
    }
}
