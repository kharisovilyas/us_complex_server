package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class Order {
    Long id;
    String name;
    Double lat;
    Double lon;
    Double alt;
    Long priority;
    Long term;

    public Order(Long id, String name, Double lat, Double lon, Double alt, Long priority, Long term) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.priority = priority;
        this.term = term;
    }
}
