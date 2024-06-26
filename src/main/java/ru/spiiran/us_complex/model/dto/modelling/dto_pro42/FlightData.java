package ru.spiiran.us_complex.model.dto.modelling.dto_pro42;

import java.util.List;
import java.util.stream.Collectors;

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
    public FlightData(List<Double> revTime, List<Double> sunTime, List<Double> eclTime) {
        this.revTime = revTime.stream()
                .map(Math::round) // Округление Double к Long
                .map(Long::valueOf)
                .collect(Collectors.toList());

        this.sunTime = sunTime.stream()
                .map(Math::round) // Округление Double к Long
                .map(Long::valueOf)
                .collect(Collectors.toList());

        this.eclTime = eclTime.stream()
                .map(Math::round) // Округление Double к Long
                .map(Long::valueOf)
                .collect(Collectors.toList());
    }
}