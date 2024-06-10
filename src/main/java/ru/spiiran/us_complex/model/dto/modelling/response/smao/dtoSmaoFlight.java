package ru.spiiran.us_complex.model.dto.modelling.response.smao;

import com.google.gson.Gson;

public class dtoSmaoFlight implements IDTOSMAOResponse{
    private Long time;
    private String type;
    private Integer idReceiver;
    private FlightState flightState;

    // Constructor from JSON
    public dtoSmaoFlight(String jsonResponse) {
        Gson gson = new Gson();
        dtoSmaoFlight response = gson.fromJson(jsonResponse, dtoSmaoFlight.class);
        this.time = response.getTime();
        this.type = response.getType();
        this.idReceiver = response.getIdReceiver();
        this.flightState = response.getFlightState();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(Integer idReceiver) {
        this.idReceiver = idReceiver;
    }

    public FlightState getFlightState() {
        return flightState;
    }

    public void setFlightState(FlightState flightState) {
        this.flightState = flightState;
    }
}
