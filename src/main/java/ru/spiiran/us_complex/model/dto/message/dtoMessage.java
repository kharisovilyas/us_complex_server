package ru.spiiran.us_complex.model.dto.message;

import ru.spiiran.us_complex.model.dto.IDTOEntity;

import java.util.Date;

public class dtoMessage implements IDTOEntity {
    private final String type;
    private final String message;
    private final Date serverDateTime;
    public dtoMessage(String type, String message) {
        this.type = type;
        this.message = message;
        this.serverDateTime = new Date();
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Date getServerDateTime() {
        return serverDateTime;
    }
}
