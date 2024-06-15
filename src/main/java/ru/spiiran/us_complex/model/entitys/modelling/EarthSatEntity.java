package ru.spiiran.us_complex.model.entitys.modelling;

import jakarta.persistence.*;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoAssessment;
import ru.spiiran.us_complex.model.entitys.constellation.SatelliteEntity;
import ru.spiiran.us_complex.model.entitys.earth.EarthPointEntity;
import ru.spiiran.us_complex.model.entitys.general.IEntity;
import ru.spiiran.us_complex.repositories.constellation.SatelliteRepository;
import ru.spiiran.us_complex.repositories.earth.EarthPointRepository;
import ru.spiiran.us_complex.repositories.modelling.EarthSatRepository;

@Entity
@Table(name = "earth_sat")
public class EarthSatEntity implements IEntity {

    @Override
    public dtoMessage getDtoMessage(String type, String message) {
        return new dtoMessage(type, message);
    }

    @Id
    @Column(name = "id_earth_sat")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long earthSatId;

    @Column(name = "begin_time")
    private Long beginTime;

    @Column(name = "end_time")
    private Long endTime;

    @ManyToOne
    @JoinColumn(name = "earth_point_id")
    private EarthPointEntity earthPoint;

    @ManyToOne
    @JoinColumn(name = "satellite_id")
    private SatelliteEntity satellite;

    public EarthSatEntity(){}

    public EarthSatEntity(
            dtoAssessment earthSat,
            EarthSatRepository earthSatRepository,
            EarthPointRepository earthPointRepository,
            SatelliteRepository satelliteRepository) {
        this.beginTime = earthSat.getBegin();
        this.endTime = earthSat.getEnd();
        earthSatRepository.save(this);
        // Находим EarthPointEntity по имени
        EarthPointEntity earthPoint = earthPointRepository.findByNameEarthPoint(earthSat.getGoalLabel());

        // Находим coArbitraryConstruction по ID
        SatelliteEntity satellite = satelliteRepository.findById(Long.parseLong(earthSat.getScLabel())).orElse(null);

        // Добавляем в списки
        if (earthPoint != null) {
            earthPoint.getEarthSatContacts().add(this);
            this.earthPoint = earthPoint;
            earthPointRepository.saveAndFlush(earthPoint);
        }
        if (satellite != null) {
            satellite.getEarthSatContacts().add(this);
            this.satellite = satellite;
            satelliteRepository.saveAndFlush(satellite);


        }
    }
    public Long getEarthSatId() {
        return earthSatId;
    }

    public void setEarthSatId(Long earthSatId) {
        this.earthSatId = earthSatId;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }


    public EarthPointEntity getEarthPoint() {
        return earthPoint;
    }

    public void setEarthPoint(EarthPointEntity earthPoint) {
        this.earthPoint = earthPoint;
    }

    public SatelliteEntity getSatellite() {
        return satellite;
    }

    public void setSatellite(SatelliteEntity satellite) {
        this.satellite = satellite;
    }
}
