package ru.spiiran.us_complex.model.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.model.entitys.general.generalStatusEntity;

import java.util.List;

@Entity
@Table(name = "constellation")
public class ConstellationEntity implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ID;
    @Column(name = "constellation_name")
    private String constellationName;
    @Column(name = "type_constellation")
    private String typeOfConstellation;
    @OneToMany(mappedBy = "constellation", cascade = CascadeType.ALL)
    private List<ConstellationDetailed> constellationDetailedList;
    @OneToMany(mappedBy = "constellation", cascade = CascadeType.ALL)
    private List<ConstellationOverview> constellationOverviewList;
    @ManyToOne
    @JoinColumn(name = "status_id")
    @JsonIgnore
    private generalStatusEntity generalStatus;

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    public String getTypeOfConstellation() {
        return typeOfConstellation;
    }

    public void setTypeOfConstellation(String typeOfConstellation) {
        this.typeOfConstellation = typeOfConstellation;
    }

    public List<ConstellationDetailed> getConstellationDetailedList() {
        return constellationDetailedList;
    }

    public void setConstellationDetailedList(List<ConstellationDetailed> constellationDetailedList) {
        this.constellationDetailedList = constellationDetailedList;
    }

    public List<ConstellationOverview> getConstellationOverviewList() {
        return constellationOverviewList;
    }

    public void setConstellationOverviewList(List<ConstellationOverview> constellationOverviewList) {
        this.constellationOverviewList = constellationOverviewList;
    }

    public generalStatusEntity getGeneralStatus() {
        return generalStatus;
    }

    public void setGeneralStatus(generalStatusEntity generalStatus) {
        this.generalStatus = generalStatus;
    }

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
