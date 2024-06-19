package ru.spiiran.us_complex.model.dto.modelling.dto_pro42;

import java.util.List;

public class FlightData {
    private int nRev;
    private List<Double> revTime;
    private List<Double> sunTime;
    private List<Double> eclTime;

    public FlightData() {}

    public List<Double> getRevTime() {
        return revTime;
    }

    public void setRevTime(List<Double> revTime) {
        this.revTime = revTime;
    }

    public List<Double> getSunTime() {
        return sunTime;
    }

    public void setSunTime(List<Double> sunTime) {
        this.sunTime = sunTime;
    }

    public List<Double> getEclTime() {
        return eclTime;
    }

    public void setEclTime(List<Double> eclTime) {
        this.eclTime = eclTime;
    }

    public int getnRev() {
        return nRev;
    }

    public void setnRev(int nRev) {
        this.nRev = nRev;
    }
}