package ru.spiiran.us_complex.model.entitys.general;

import ru.spiiran.us_complex.model.dto.message.dtoMessage;

public interface IEntity{
    long getID();
    dtoMessage getDtoMessage(String type, String message);
}
