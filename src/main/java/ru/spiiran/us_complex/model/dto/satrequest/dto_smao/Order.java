package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class Order {
    Long orderId;
    String name;
    Double latitude;
    Double longitude;
    Double altitude;
    Long priority;
    Long term;

    public Order(Long orderId, String name, Double latitude, Double longitude, Double altitude, Long priority, Long term) {
        this.orderId = orderId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.priority = priority;
        this.term = term;
    }
}
