package ru.spiiran.us_complex.model.dto.constellation;

import ru.spiiran.us_complex.model.dto.IDTOEntity;

public class dtoPlanarConstellation implements IDTOEntity, IDTOConstellation {
    private String constellationName;
    private Boolean isArbitraryFormation;
    private dtoPlanarPrm parametersCalculation;

    public dtoPlanarConstellation() {}

    @Override
    public String getConstellationName() {
        return constellationName;
    }
    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    @Override
    public Boolean getArbitraryFormation() {
        return isArbitraryFormation;
    }
    public void setArbitraryFormation(Boolean arbitraryFormation) {
        isArbitraryFormation = arbitraryFormation;
    }

    public dtoPlanarPrm getParametersCalculation() {
        return parametersCalculation;
    }

    public void setParametersCalculation(dtoPlanarPrm parametersCalculation) {
        this.parametersCalculation = parametersCalculation;
    }
}
