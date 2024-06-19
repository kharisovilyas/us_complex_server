package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;

public class Satellite {
    private Long id;
    private Long groupId;
    private Long modelId;
    private Integer plane;
    private Integer pos;
    private Double a;
    private Double e;
    private Double i;
    private Double Q;
    private Double w;
    private Double u;
    private String type;

    public Satellite() {}

    public Satellite(SatelliteEntity satellite) {
        this.id = satellite.getIdNodeEntity().getNodeId();
        this.plane = 1;
        this.pos = 1;
        this.groupId = satellite.getConstellation().getConstellationId();
        this.modelId = 1L; //TODO: после выполнения спринта модель-КА
        this.a = satellite.getAltitude();
        this.e = satellite.getEccentricity();
        this.i = satellite.getIncline();
        this.Q = satellite.getLongitudeAscendingNode();
        this.w = satellite.getPerigeeWidthArgument();
        this.u = satellite.getTrueAnomaly();
        this.type = satellite.getIdNodeEntity().getNodeType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getPlane() {
        return plane;
    }

    public void setPlane(Integer plane) {
        this.plane = plane;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Double getA() {
        return a;
    }

    public void setA(Double a) {
        this.a = a;
    }

    public Double getE() {
        return e;
    }

    public void setE(Double e) {
        this.e = e;
    }

    public Double getI() {
        return i;
    }

    public void setI(Double i) {
        this.i = i;
    }

    public Double getQ() {
        return Q;
    }

    public void setQ(Double q) {
        Q = q;
    }

    public Double getW() {
        return w;
    }

    public void setW(Double w) {
        this.w = w;
    }

    public Double getU() {
        return u;
    }

    public void setU(Double u) {
        this.u = u;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}