package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class Satellite {
    Long id;
    Long constellation;
    Long model;
    Integer plane;
    Integer pos;
    Double a;
    Double e;
    Double i;
    Double Q;
    Double w;
    Double u;
    String type;

    public Satellite(Long id, Long constellation, Long model, Integer plane, Integer pos, Double a, Double e, Double i, Double Q, Double w, Double u, String type) {
        this.id = id;
        this.plane = plane;
        this.pos = pos;
        this.constellation = constellation;
        this.model = model;
        this.a = a;
        this.e = e;
        this.i = i;
        this.Q = Q;
        this.w = w;
        this.u = u;
        this.type = type;
    }
}