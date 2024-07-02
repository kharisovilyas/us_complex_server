package ru.spiiran.us_complex.model.dto.modelsat.old.power;

import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.dto.modelsat.dtoModelSat;
import ru.spiiran.us_complex.model.dto.modelsat.old.equipment.dtoEquipment;
import ru.spiiran.us_complex.model.dto.modelsat.old.equipment.dtoEquipmentType;

import java.util.List;

public class dtoPowerTable implements IDTOEntity {
    private List<dtoEquipment> equipmentList;
    private List<dtoEquipmentType> equipmentTypeList;
    private dtoModelSat modelSat;
    private List<dtoMode> modeList;

    public dtoPowerTable() {
    }

    public List<dtoEquipment> getEquipmentList() {
        return equipmentList;
    }

    public void setDtoEquipmentList(List<dtoEquipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<dtoEquipmentType> getEquipmentTypeList() {
        return equipmentTypeList;
    }

    public void setEquipmentTypeList(List<dtoEquipmentType> equipmentTypeList) {
        this.equipmentTypeList = equipmentTypeList;
    }

    public dtoModelSat getModelSat() {
        return modelSat;
    }

    public void setModelSat(dtoModelSat modelSat) {
        this.modelSat = modelSat;
    }

    public List<dtoMode> getDtoMode() {
        return modeList;
    }

    public void setDtoMode(List<dtoMode> modeList) {
        this.modeList = modeList;
    }
}
