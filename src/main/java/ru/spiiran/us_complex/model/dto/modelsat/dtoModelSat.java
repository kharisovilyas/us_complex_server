package ru.spiiran.us_complex.model.dto.modelsat;

import org.springframework.web.multipart.MultipartFile;
import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.dto.constellation.dtoSatellite;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;

import java.util.List;
import java.util.stream.Collectors;

public class dtoModelSat implements IDTOEntity {
    private Long id;
    private String modelName;
    private String description;
    private MultipartFile imageFile;
    private dtoTargetEquipment targetEquipment;
    private dtoOnBoardDevice onBoardDevice;
    private List<dtoOperatingParameter> operatingParameter;

    private List<dtoSatellite> satellites;

    public dtoModelSat(){}

    public dtoModelSat(ModelSatEntity modelSatEntity){
        this.id = modelSatEntity.getModelId();
        this.description = modelSatEntity.getDescription();
        this.modelName = modelSatEntity.getModelName();
        this.description = modelSatEntity.getDescription();
        this.operatingParameter = modelSatEntity.getOperatingParameter().stream().map(dtoOperatingParameter::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public dtoTargetEquipment getTargetEquipment() {
        return targetEquipment;
    }

    public void setTargetEquipment(dtoTargetEquipment targetEquipment) {
        this.targetEquipment = targetEquipment;
    }

    public dtoOnBoardDevice getOnBoardDevice() {
        return onBoardDevice;
    }

    public void setOnBoardDevice(dtoOnBoardDevice onBoardDevice) {
        this.onBoardDevice = onBoardDevice;
    }

    public List<dtoOperatingParameter> getOperatingParameter() {
        return operatingParameter;
    }

    public void setOperatingParameter(List<dtoOperatingParameter> operatingParameter) {
        this.operatingParameter = operatingParameter;
    }
}
