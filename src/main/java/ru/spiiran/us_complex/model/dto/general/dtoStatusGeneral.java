package ru.spiiran.us_complex.model.dto.general;

public class dtoStatusGeneral {
    private Long statusId;
    private Boolean statusOfEditEarth;
    private Boolean statusOfEditConstellation;

    public dtoStatusGeneral() {}

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Boolean getStatusOfEditEarth() {
        return statusOfEditEarth;
    }

    public void setStatusOfEditEarth(Boolean statusOfEditEarth) {
        this.statusOfEditEarth = statusOfEditEarth;
    }

    public Boolean getStatusOfEditConstellation() {
        return statusOfEditConstellation;
    }

    public void setStatusOfEditConstellation(Boolean statusOfEditConstellation) {
        this.statusOfEditConstellation = statusOfEditConstellation;
    }
}
