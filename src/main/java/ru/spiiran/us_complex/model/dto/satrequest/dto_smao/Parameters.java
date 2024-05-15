package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class Parameters {
    Long t0;
    Long modelingBegin;
    Long modelingEnd;
    Long modelingTimeStep;
    Boolean interSatelliteCommunication;
    String controlSystem;
    Long duration;

    public Parameters(Long t0, Long modelingBegin, Long modelingEnd, Long modelingTimeStep, Boolean interSatelliteCommunication, String controlSystem, Long duration) {
        this.t0 = t0;
        this.modelingBegin = modelingBegin;
        this.modelingEnd = modelingEnd;
        this.modelingTimeStep = modelingTimeStep;
        this.interSatelliteCommunication = interSatelliteCommunication;
        this.controlSystem = controlSystem;
        this.duration = duration;
    }
}
