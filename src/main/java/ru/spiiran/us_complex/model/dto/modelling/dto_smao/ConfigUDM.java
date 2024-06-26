package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

public class ConfigUDM {
    private Long nWheels;
    private Double accel;
    private Double rate;
    private Double stabTime;

    public ConfigUDM() {
    }

    public ConfigUDM(Long nWheels, Double accel, Double rate, Double stabTime) {
        this.nWheels = nWheels;
        this.accel = accel;
        this.rate = rate;
        this.stabTime = stabTime;
    }

    public Long getnWheels() {
        return nWheels;
    }

    public void setnWheels(Long nWheels) {
        this.nWheels = nWheels;
    }

    public Double getAccel() {
        return accel;
    }

    public void setAccel(Double accel) {
        this.accel = accel;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getStabTime() {
        return stabTime;
    }

    public void setStabTime(Double stabTime) {
        this.stabTime = stabTime;
    }
}
