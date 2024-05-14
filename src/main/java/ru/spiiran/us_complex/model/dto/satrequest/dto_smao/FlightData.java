package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

import java.util.List;

public class FlightData {
    private int nRev;
    private List<Long> revTime;
    private List<Long> sunTime;
    private List<Long> eclTime;

    public int getnRev() {
        return nRev;
    }

    public void setnRev(int nRev) {
        this.nRev = nRev;
    }

    public List<Long> getRevTime() {
        return revTime;
    }

    public void setRevTime(List<Long> revTime) {
        this.revTime = revTime;
    }

    public List<Long> getSunTime() {
        return sunTime;
    }

    public void setSunTime(List<Long> sunTime) {
        this.sunTime = sunTime;
    }

    public List<Long> getEclTime() {
        return eclTime;
    }

    public void setEclTime(List<Long> eclTime) {
        this.eclTime = eclTime;
    }

    public FlightData() {}
}