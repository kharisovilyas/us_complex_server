package ru.spiiran.us_complex.model.entitys.general;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.general.dtoStatusGeneral;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.constellation.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;

import java.util.List;

@Entity
@Table(name = "status_general")
public class generalStatusEntity implements IEntity {
    @Id
    @Column(name = "status_id")
    private Long statusId;
    @Column(name = "status_edit_earth")
    private Boolean statusOfEditEarth;
    @Column(name = "status_edit_constellation")
    private Boolean statusOfEditConstellation;
    @OneToMany(mappedBy = "generalStatus", cascade = CascadeType.ALL)
    private List<EarthPointEntity> earthPointEntities;
    @OneToMany(mappedBy = "generalStatus", cascade = CascadeType.ALL)
    private List<ConstellationEntity> constellationEntities;

    public generalStatusEntity(){
        this.statusId = 1L;
    }

    public generalStatusEntity(dtoStatusGeneral dto) {
        this.statusId = dto.getStatusId();
        this.statusOfEditConstellation = dto.getStatusOfEditConstellation();
        this.statusOfEditEarth = dto.getStatusOfEditEarth();

    }

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

    public List<EarthPointEntity> getEarthPointEntities() {
        return earthPointEntities;
    }

    public void setEarthPointEntities(List<EarthPointEntity> earthPointEntities) {
        this.earthPointEntities = earthPointEntities;
    }

    @Override
    public Long getID() {
        return statusId;
    }
    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    public List<ConstellationEntity> getConstellationEntities() {
        return constellationEntities;
    }

    public void setConstellationEntities(List<ConstellationEntity> constellationEntities) {
        this.constellationEntities = constellationEntities;
    }
}
