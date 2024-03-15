package ru.spiiran.us_complex.model.entitys.modelsat.power;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name = "ms_mode")
public class msModeEntity implements IEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Column(name = "r1")
    private String R1;
    @Column(name = "r2")
    private String R2;
    @Column(name = "r3")
    private String R3;
    @ManyToOne
    @JoinColumn(name="power_id")
    private msPowerEntity msPowerEntity;

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
