package ru.spiiran.us_complex.controllers.api.v1.modelling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spiiran.us_complex.model.dto.IDTOEntity;
import ru.spiiran.us_complex.model.dto.message.dtoMessage;
import ru.spiiran.us_complex.model.dto.modelling.request.dtoViewWindowRequest;
import ru.spiiran.us_complex.services.connect.ModellingModulesService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/modelling")
public class RestModellingController {
    @Autowired
    private ModellingModulesService modellingModulesService;
    @GetMapping("/satellite")
    public ResponseEntity<List<String>> modellingSatellite(){
        return ResponseEntity.ok().body(modellingModulesService.modellingOneSat());
    }

    @GetMapping("/constellation/sat-earth")
    public ResponseEntity<dtoMessage> modellingConstSatEarth(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationSatEarth());
    }

    @GetMapping("/constellation/net")
    public ResponseEntity<dtoMessage> modellingConstNet(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationNetwork(null));
    }

    @GetMapping("/constellation/sat-earth/delivery")
    public ResponseEntity<dtoMessage> modellingConstSatEarthDelivery(){
        return ResponseEntity.ok().body(modellingModulesService.modellingConstellationDelivery(null));
    }

    @GetMapping("/view/earth")
    public ResponseEntity<dtoMessage> modellingViewEarthPoint(){
        return ResponseEntity.ok().body(modellingModulesService.assessmentEarthSatModelling());

    }
    @PostMapping("/view/request")
    public ResponseEntity<List<IDTOEntity>> modellingViewRequest(@RequestBody dtoViewWindowRequest dtoViewWindowRequest){
        return ResponseEntity.ok().body(modellingModulesService.assessmentConstellation(dtoViewWindowRequest));
    }
}
