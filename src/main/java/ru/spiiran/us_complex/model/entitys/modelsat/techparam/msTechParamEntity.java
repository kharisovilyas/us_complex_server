package ru.spiiran.us_complex.model.entitys.modelsat.techparam;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="ms_tech_param")
public class msTechParamEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @ElementCollection
    @CollectionTable(name = "additional_columns", joinColumns = @JoinColumn(name = "entity_id"))
    @MapKeyColumn(name = "column_name")
    @Column(name = "column_value")
    private Map<msListOfParam, String> additionalColumns = new HashMap<>();

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return null;
    }
}
