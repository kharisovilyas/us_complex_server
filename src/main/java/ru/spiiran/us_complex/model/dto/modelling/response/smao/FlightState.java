package ru.spiiran.us_complex.model.dto.modelling.response.smao;

public class FlightState {
    private Integer satId;
    private Long time;
    private Boolean isEarthConnect;
    private Integer sendSatId;
    private String mode;

    public FlightState() {
    }

    public Integer getSatId() {
        return satId;
    }

    public void setSatId(Integer satId) {
        this.satId = satId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Boolean getEarthConnect() {
        return isEarthConnect;
    }

    public void setEarthConnect(Boolean earthConnect) {
        isEarthConnect = earthConnect;
    }

    public Integer getSendSatId() {
        return sendSatId;
    }

    public void setSendSatId(Integer sendSatId) {
        this.sendSatId = sendSatId;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
