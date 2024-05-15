package ru.spiiran.us_complex.model.dto.satrequest.dto_smao;

public class Satellite {
    Long satelliteId;
    Long constellation;
    Long modelSatellite;
    Integer plane;
    Integer position;
    Double altitude;
    Double eccentricity;
    Double incline;
    Double longitudeAscendingNode;
    Double perigeeWidthArgument;
    Double trueAnomaly;
    String nodeType;

    public Satellite(Long satelliteId, Long constellation, Long modelSatellite, Integer plane, Integer position, Double altitude, Double eccentricity, Double incline, Double longitudeAscendingNode, Double perigeeWidthArgument, Double trueAnomaly, String nodeType) {
        this.satelliteId = satelliteId;
        this.constellation = constellation;
        this.modelSatellite = modelSatellite;
        this.plane = plane;
        this.position = position;
        this.altitude = altitude;
        this.eccentricity = eccentricity;
        this.incline = incline;
        this.longitudeAscendingNode = longitudeAscendingNode;
        this.perigeeWidthArgument = perigeeWidthArgument;
        this.trueAnomaly = trueAnomaly;
        this.nodeType = nodeType;
    }
}