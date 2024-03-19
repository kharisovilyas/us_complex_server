package ru.spiiran.us_complex.model.dto.modelsat;

import org.springframework.web.multipart.MultipartFile;
import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelSatEntity;

public class dtoModelSat implements IDTOEntity {
    private Long id;
    private String modelName;
    private String description;
    private MultipartFile imageFile;

    public dtoModelSat(){}

    public dtoModelSat(ModelSatEntity modelSatEntity){
        this.description = modelSatEntity.getDescription();
        this.modelName = modelSatEntity.getModelName();
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
}
