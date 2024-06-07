package ru.spiiran.us_complex.model.entitys.modelsat;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.power.msPowerEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.techparam.msTechParamEntity;


@Entity
@Table(name = "model_sat")
public class ModelSatEntity implements IEntity {
    @Id
    @Column(name = "id_model")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "description")
    private String description;

    @Column(name = "image_file_name")
    private String imageFileName;
    @ManyToOne
    @JoinColumn(name = "tech_param_id")
    private msTechParamEntity msTechParamEntity;

    @ManyToOne
    @JoinColumn(name = "power_id")
    private msPowerEntity msPowerEntity;

    public ModelSatEntity() {
    }

    public ModelSatEntity(dtoModelSat dtoModelSat) {
        this.modelName = dtoModelSat.getModelName();
        this.description = dtoModelSat.getDescription();
    }


    public void setID(Long ID) {
        this.modelId = ID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return null;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}
