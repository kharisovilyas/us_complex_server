package ru.spiiran.us_complex.controllers.api.v1.modelling;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spiiran.us_complex.model.dto.modelling.response.pro42.dtoEarthSat;
import ru.spiiran.us_complex.services.connect.ModellingDatabaseService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/modelling/data")
public class RestModellingDataController {
    @Autowired
    private ModellingDatabaseService modellingDatabaseService;

    @GetMapping("/earth-sat/all")
    public ResponseEntity<List<dtoEarthSat>> getAllEarthSat(){
        return ResponseEntity.ok().body(modellingDatabaseService.getAllAssessmentEarthSat());
    }

    @GetMapping("/earth-sat/byConstellation")
    public ResponseEntity<List<dtoEarthSat>> getEarthSatByConstellation(@RequestParam Long constellationId){
        return ResponseEntity.ok().body(modellingDatabaseService.getEarthSatByConstellationId(constellationId));
    }

}
