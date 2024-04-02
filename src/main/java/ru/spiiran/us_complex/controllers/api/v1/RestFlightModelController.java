package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.services.FlightModelService;

@Controller
@RequestMapping("/api/v1/bridge")
public class RestFlightModelController {
    @Autowired private FlightModelService flightModelService;
    @PostMapping("start/modelling")
    public ResponseEntity<dtoMessage> startModelling(){
        return flightModelService.startModelling();
    }
}
