package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

import java.util.List;

public class Event {
    String eventType;
    Node node;
    List<Satellite> satellites;
    Parameters parameters;

    FlightData flightData;

    boolean isSendingLogs;

    public Event(String eventType, Node node, List<Satellite> satellites, Parameters parameters, boolean isSendingLogs) {
        this.eventType = eventType;
        this.node = node;
        this.satellites = satellites;
        this.parameters = parameters;
        this.isSendingLogs = isSendingLogs;
    }
    public Event(String eventType, Node node, List<Satellite> satellites, Parameters parameters, boolean isSendingLogs, FlightData flightData) {
        this.eventType = eventType;
        this.node = node;
        this.satellites = satellites;
        this.parameters = parameters;
        this.isSendingLogs = isSendingLogs;
        this.flightData = flightData;
    }
}
