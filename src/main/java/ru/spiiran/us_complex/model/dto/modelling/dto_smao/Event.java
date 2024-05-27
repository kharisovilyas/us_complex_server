package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

import java.util.List;

public class Event {
    String type;
    Node node;
    List<Satellite> satellites;
    Parameters parameters;

    FlightData flightData;

    boolean isSendingLogs;

    public Event(String type, Node node, List<Satellite> satellites, Parameters parameters, boolean isSendingLogs) {
        this.type = type;
        this.node = node;
        this.satellites = satellites;
        this.parameters = parameters;
        this.isSendingLogs = isSendingLogs;
    }
    public Event(String type, Node node, List<Satellite> satellites, Parameters parameters, boolean isSendingLogs, FlightData flightData) {
        this.type = type;
        this.node = node;
        this.satellites = satellites;
        this.parameters = parameters;
        this.isSendingLogs = isSendingLogs;
        this.flightData = flightData;
    }
}
