package ru.spiiran.us_complex.model.dto.modelling.dto_smao;

import java.util.List;

public class EventRequest {
    String type;
    Long time;
    List<Order> order;
    Long targetId;
    public EventRequest(Long time, List<Order> order, String type, Long targetId) {
        this.time = time;
        this.order = order;
        this.type = type;
        this.targetId = targetId;
    }
}
