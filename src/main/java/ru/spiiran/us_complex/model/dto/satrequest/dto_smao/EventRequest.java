package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class EventRequest {
    Long timeEventOccurred;
    Params eventParameters;
    String eventType;
    Long targetId;
    public EventRequest(Long timeEventOccurred, Params eventParameters, String eventType, Long targetId) {
        this.timeEventOccurred = timeEventOccurred;
        this.eventParameters = eventParameters;
        this.eventType = eventType;
        this.targetId = targetId;
    }
}
