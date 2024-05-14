package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class EventRequest {
    Long time;
    Params params;
    String event_type;
    Long targetId;
    public EventRequest(Long time, Params params, String event_type, Long targetId) {
        this.time = time;
        this.params = params;
        this.event_type = event_type;
        this.targetId = targetId;
    }
}
