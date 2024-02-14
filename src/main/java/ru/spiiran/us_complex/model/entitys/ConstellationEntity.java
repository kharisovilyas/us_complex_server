package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name="constellation")
public class ConstellationEntity implements IEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String name;
    private String satelliteModel;
    private String structureType;
    private Long idModel;
    @Override
    public long getID() {
        return ID;
    }
    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }


}