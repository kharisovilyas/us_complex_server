package ru.spiiran.us_complex.model.entitys;

import jakarta.persistence.*;

@Entity
@Table(name = "earth_sat")
public class EarthSatEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "id_node")
    private Long idNode;

    @Column(name = "satellite_id")
    private Long satelliteId;

    @Column(name = "begin_time")
    private String beginTime;

    @Column(name = "end_time")
    private String endTime;

    public EarthSatEntity() {
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getIdNode() {
        return idNode;
    }

    public void setIdNode(Long idNode) {
        this.idNode = idNode;
    }

    public Long getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(Long satelliteId) {
        this.satelliteId = satelliteId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
