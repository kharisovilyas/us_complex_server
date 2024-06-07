package ru.spiiran.us_complex.model.entitys.satrequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.satrequest.dtoCatalog;
import ru.spiiran.us_complex.model.entitys.general.IEntity;

import java.util.List;

@Entity
@Table(name = "sr_catalog")
public class CatalogEntity implements IEntity {

    @Id
    @Column(name = "id_goal")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long goalId;

    @Column(name = "goal_name")
    private String goalName;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lon")
    private Double lon;

    @Column(name = "alt")
    private Double alt;

    @OneToMany(mappedBy = "catalogEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<RequestEntity> requestEntityList;

    public CatalogEntity() {
    }

    public CatalogEntity(dtoCatalog catalog) {
        this.goalId = catalog.getGoalId();
        this.alt = catalog.getAlt();
        this.lat = catalog.getLat();
        this.goalName = catalog.getGoalName();
        this.lon = catalog.getLon();
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getAlt() {
        return alt;
    }

    public void setAlt(Double alt) {
        this.alt = alt;
    }

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }
}
