package ru.spiiran.us_complex.model.entitys.modelsat.equipment;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

@Entity
@Table(name="ms_type_equipment")
public class msEquipmentTypeEntity implements IEntity {
    @Id
    @Column(name = "equip_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipID;
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private msEquipmentEntity msEquipmentEntity;
    @Column(name = "type_1")
    private String type1;
    @Column(name = "type_2")
    private String type2;
    @Column(name = "type_3")
    private String type3;
    @Override
    public Long getID() {
        return equipID;
    }
    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
