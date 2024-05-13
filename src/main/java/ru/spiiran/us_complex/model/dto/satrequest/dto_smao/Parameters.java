package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class Parameters {
    Long t0;
    Long H_begin;
    Long H_end;
    Long modelingTimeStep;
    Boolean Inter_sat;
    String Control;
    Long duration;

    public Parameters(Long t0, Long h_begin, Long h_end, Long modelingTimeStep, Boolean inter_sat, String control, Long duration) {
        this.t0 = t0;
        this.H_begin = h_begin;
        this.H_end = h_end;
        this.modelingTimeStep = modelingTimeStep;
        this.Inter_sat = inter_sat;
        this.Control = control;
        this.duration = duration;
    }
}
