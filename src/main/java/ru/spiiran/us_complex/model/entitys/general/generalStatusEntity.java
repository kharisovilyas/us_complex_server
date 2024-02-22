package ru.spiiran.us_complex.model.entitys.general;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.ConstellationDetailed;
import ru.spiiran.us_complex.model.entitys.ConstellationEntity;
import ru.spiiran.us_complex.model.entitys.EarthPointEntity;

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
    private List<ConstellationDetailed> constellationDetailedList;
    @OneToMany(mappedBy = "generalStatus", cascade = CascadeType.ALL)
    private List<ConstellationEntity> constellationEntities;

    public generalStatusEntity(){
        this.statusId = 1L;
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
}
