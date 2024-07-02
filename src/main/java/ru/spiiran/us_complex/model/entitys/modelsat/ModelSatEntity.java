package ru.spiiran.us_complex.model.entitys.modelsat;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.dto.modelsat.dtoOperatingParameter;
import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.repositories.modelsat.OperatingParametersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
    @JoinColumn(name = "id_target_equipment")
    private msTargetEquipment targetEquipment;

    @ManyToOne
    @JoinColumn(name = "id_on_board_device")
    private msOnBoardDevice onBoardDevice;

    @OneToMany
    @JoinColumn(name = "id_op_parameters")
    private List<msOperatingParameter> operatingParameters;

    @OneToMany(mappedBy = "modelSat", cascade = CascadeType.ALL)
    private List<SatelliteEntity> satelliteEntities;

    public ModelSatEntity() {
    }

    public ModelSatEntity(dtoModelSat modelSat) {
        this.modelId = modelSat.getId();
        this.modelName = modelSat.getModelName();
        this.description = modelSat.getDescription();
        this.imageFileName = "";
        this.operatingParameters = new ArrayList<>();
    }

    public ModelSatEntity(dtoModelSat modelSat, OperatingParametersRepository operatingParametersRepository) {
        this.modelId = modelSat.getId();
        this.modelName = modelSat.getModelName();
        this.description = modelSat.getDescription();
        this.imageFileName = "";
        this.operatingParameters = operatingParametersRepository
                        .saveAll(
                                modelSat.getOperatingParameter().stream().map(msOperatingParameter::new).collect(Collectors.toList())
                        );
    }

    public ModelSatEntity(
            ModelSatEntity modelSatEntity,
            List<dtoOperatingParameter> operatingParameter,
            OperatingParametersRepository operatingParametersRepository
    ) {
        this.modelId = modelSatEntity.getModelId();
        this.modelName = modelSatEntity.getModelName();
        this.description = modelSatEntity.getDescription();
        this.imageFileName = "";
        this.operatingParameters = operatingParameter.stream().map(msOperatingParameter::new).collect(Collectors.toList());
    }


    public msTargetEquipment getTargetEquipment() {
        return targetEquipment;
    }

    public void setTargetEquipment(msTargetEquipment targetEquipment) {
        this.targetEquipment = targetEquipment;
    }

    public msOnBoardDevice getOnBoardDevice() {
        return onBoardDevice;
    }

    public void setOnBoardDevice(msOnBoardDevice onBoardDevice) {
        this.onBoardDevice = onBoardDevice;
    }


    public List<msOperatingParameter> getOperatingParameter() {
        return operatingParameters;
    }

    public void setOperatingParameter(List<msOperatingParameter> operatingParameters) {
        this.operatingParameters = operatingParameters;
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

    public List<SatelliteEntity> getSatelliteEntities() {
        return satelliteEntities;
    }

    public void setSatelliteEntities(List<SatelliteEntity> satelliteEntities) {
        this.satelliteEntities = satelliteEntities;
    }
}
