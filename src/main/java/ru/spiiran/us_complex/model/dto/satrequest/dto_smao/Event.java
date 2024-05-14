package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

import java.util.List;

public class Event {
    String event_type;
    Node node;
    List<Satellite> satellites;
    Parameters parameters;

    FlightData flightData;
    public FlightData getFlightData() {
        return flightData;
    }

    public void setFlightData(FlightData flightData) {
        this.flightData = flightData;
    }

    boolean sendLogs;

    public Event(String event_type, Node node, List<Satellite> satellites, Parameters parameters, boolean sendLogs) {
        this.event_type = event_type;
        this.node = node;
        this.satellites = satellites;
        this.parameters = parameters;
        this.sendLogs = sendLogs;
    }
}
