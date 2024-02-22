package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.security.Timestamp;

@Entity
@Table(name = "system")
public class SystemEntity implements IEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private Boolean earthStatus;
    private Boolean constellationStatus;
    private Boolean earthSatStatus;
    private Boolean satSatStatus;
    private Boolean gridStatus;
    private String timeModelingHorizon;
    private Timestamp startTime;
    private Timestamp modelingBegin;
    private Timestamp modelingEnd;
    private Boolean interSatelliteCommunication;
    private String controlSystem;

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    // getters and setters
}
