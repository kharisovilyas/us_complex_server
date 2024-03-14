package ru.spiiran.us_complex.model.dto.modelsat;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.entitys.modelsat.ModelEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class dtoModelSat implements IDTOEntity {
    private Long id;
    private String modelName;
    private String description;
    private byte[] imageContent;

    public dtoModelSat(){}

    public dtoModelSat(ModelEntity modelEntity){
        this.description = modelEntity.getDescription();
        this.modelName = modelEntity.getModelName();
        this.imageContent = getImageFromPath(modelEntity.getPathToImage());
    }

    private byte[] getImageFromPath(String pathToImage) {
        try {
            return Files.readAllBytes(new File(pathToImage).toPath());
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
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

    public byte[] getImageContent() {
        return imageContent;
    }

    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }
}
