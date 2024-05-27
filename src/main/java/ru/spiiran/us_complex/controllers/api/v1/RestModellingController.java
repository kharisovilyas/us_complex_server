package ru.spiiran.us_complex.controllers.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.services.satrequest.connect.ModellingModulesService;

@Controller
@RequestMapping("/api/v1/modelling")
public class RestModellingController {
    @Autowired
    private ModellingModulesService modellingModulesService;
    @PostMapping("/framework/satellite")
    public ResponseEntity<dtoMessage> frameworkSatellite(){
        return ResponseEntity.ok().body(modellingModulesService.modellingOneSat());
    }
    @PostMapping("/framework/constellation/sat-earth")
    public ResponseEntity<dtoMessage> frameworkConstSatEarth(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationSatEarth());
    }

    @PostMapping("/framework/constellation/net")
    public ResponseEntity<dtoMessage> frameworkConstNet(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationNetwork());
    }

    @PostMapping("/framework/constellation/sat-earth/delivery")
    public ResponseEntity<dtoMessage> frameworkConstSatEarthDelivery(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationDelivery());
    }
}
