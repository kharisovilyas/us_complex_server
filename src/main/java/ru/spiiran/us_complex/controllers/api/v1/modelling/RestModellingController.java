package ru.spiiran.us_complex.controllers.api.v1.modelling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.services.connect.ModellingModulesService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/modelling")
public class RestModellingController {
    @Autowired
    private ModellingModulesService modellingModulesService;
    @GetMapping("/satellite")
    public ResponseEntity<List<String/*IDTOSMAOResponse*/>> frameworkSatellite(){
        return ResponseEntity.ok().body(modellingModulesService.modellingOneSat());
    }

    @GetMapping("/constellation/sat-earth")
    public ResponseEntity<dtoMessage> frameworkConstSatEarth(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationSatEarth(null));
    }

    @GetMapping("/constellation/net")
    public ResponseEntity<dtoMessage> frameworkConstNet(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationNetwork(null));
    }

    @GetMapping("/constellation/sat-earth/delivery")
    public ResponseEntity<dtoMessage> frameworkConstSatEarthDelivery(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationDelivery(null));
    }

    @GetMapping("/view/earth")
    public ResponseEntity<List<String>/*dtoMessage*/> frameworkViewEarthPoint(){
        return ResponseEntity.ok().body(modellingModulesService.assessmentEarthSat(null));

    }

    @GetMapping("/view/request")
    public ResponseEntity<List<String>/*dtoMessage*/> frameworkViewRequest(){
        return ResponseEntity.ok().body(modellingModulesService.assessmentConstellation(null));
    }
}
