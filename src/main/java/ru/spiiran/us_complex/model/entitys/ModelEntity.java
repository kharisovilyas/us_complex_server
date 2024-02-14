package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;


@Entity
@Table(name = "model")
public class ModelEntity implements IEntity {

    @Id
    @Column(name = "satellite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long satelliteId;

    @Column(name = "model_name")
    private String modelName;

    @Override
    public long getID() {
        return 0;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return null;
    }

    // Геттеры и сеттеры
}
